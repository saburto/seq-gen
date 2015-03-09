package org.saburto.seqxmi.gen.output;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.saburto.seqxmi.gen.input.InvocationInfo;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestXMIBuilder {

	private XMIBuilder builder;

	@Before
	public void setup() {
		builder = new XMIBuilder();
	}

	@Test
	public void test_Add_n_lifetimes_XMI() throws Exception {
		builder.setPackage("package1");
		for (int i = 0; i < 10; i++) {
			builder.addLifeline("Clase" + i);
		}
		String xmiActual = builder.build();
		for (int i = 0; i < 10; i++) {	
			//<lifeline xmi:type="uml:Lifeline" xmi:id="EAID_915A0338_3D6D_47bd_B144_45750FDDA54A" name="Object1" visibility="public" represents="EAID_AT000000_3D6D_47bd_B144_45750FDDA54A"/>
			Map<String, Matcher<String>> map = new HashMap<String, Matcher<String>>();
			map.put("xmi:type", equalTo("uml:Lifeline"));
			map.put("name", equalTo("Clase" + i));
			map.put("xmi:id", not(""));
			map.put("represents", not(""));

			assertTagWithAttributes(xmiActual, "lifeline", i, map);	
		}
		
		
	}

	@Test
	public void test_Add_one_invocation_info_lifetime_XMI() throws Exception {
		InvocationInfo to = new InvocationInfo();
		to.setName("motodo1");
		String name = "Clase2";
		to.setType(name);
		to.setReturnType("int");
		
		String xmiActual = builder.setPackage("package1").addMessage("Clase1", to).build();
		assertLifeline("Clase1", xmiActual, 0);
		assertLifeline(name, xmiActual, 1);
		
		
		//<message xmi:type="uml:Message" xmi:id="EAID_4AC126E4_E3B7_4289_BF00_DADABCAE693E" name="Meotod1" messageKind="complete" messageSort="synchCall" sendEvent="EAID_FR000000_3D6D_47bd_B144_45750FDDA54A" receiveEvent="EAID_FR000000_5F1B_43d5_896E_0829E71FEBFD"/>
		//<message xmi:type="uml:Message" xmi:id="EAID_BB4DB84A_5727_4474_90D3_76411716FC71" messageKind="complete" messageSort="reply" sendEvent="EAID_FR000003_5F1B_43d5_896E_0829E71FEBFD" receiveEvent="EAID_FR000003_3D6D_47bd_B144_45750FDDA54A"/>
		
		assertMessage(xmiActual, 0, to.getName());
		
		assertMessageReply(xmiActual, 1);
		
	}

	@Test
	public void test_Add_one_lifetime_XMI() throws Exception {
		String xmiActual = builder.setPackage("package1").addLifeline("Clase1").build();
		
		//<lifeline xmi:type="uml:Lifeline" xmi:id="EAID_915A0338_3D6D_47bd_B144_45750FDDA54A" name="Object1" visibility="public" represents="EAID_AT000000_3D6D_47bd_B144_45750FDDA54A"/>
		Map<String, Matcher<String>> map = new HashMap<String, Matcher<String>>();
		map.put("xmi:type", equalTo("uml:Lifeline"));
		map.put("name", equalTo("Clase1"));
		map.put("xmi:id", not(""));
		map.put("represents", any(String.class));

		assertTagWithAttributes(xmiActual, "lifeline", 0, map);
		
	}
	
	
	
	@Test
	public void test_Add_Pacakge_XMI() throws Exception {
		String xmiActual = builder.setPackage("package1").build();
		String tagname = "packagedElement";

		Map<String, Matcher<String>> map = new HashMap<String, Matcher<String>>();
		map.put("xmi:type", equalTo("uml:Package"));
		map.put("name", equalTo("package1"));
		map.put("xmi:id", not(""));

		assertTagWithAttributes(xmiActual, tagname, 0, map);
		
		map.clear();
		map.put("xmi:type", equalTo("uml:Collaboration"));
		map.put("name", equalTo("EA_Collaboration1"));
		map.put("xmi:id", not(""));

		assertTagWithAttributes(xmiActual, tagname, 1, map);
		
		map.clear();
		map.put("xmi:type", equalTo("uml:Interaction"));
		map.put("name", equalTo("EA_Interaction1"));
		map.put("xmi:id", not(""));

		assertTagWithAttributes(xmiActual, "ownedBehavior", 0, map);
		

	}
	
	@Test
	public void test_Build_Empty_XMI() throws Exception {
		String xmiActual = builder.build();
		assertThat(
				xmiActual,
				containsString("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<xmi:XMI xmlns:xmi=\"http://schema.omg.org/spec/XMI/2.1\" xmlns:uml=\"http://schema.omg.org/spec/UML/2.1\" xmi:version=\"2.1\""));
	}
	
	@Test
	public void test_Build_Model_XMI() throws Exception {
		String xmiActual = builder.build();
		assertThat(
				xmiActual,
				containsString("<uml:Model name=\"EA_Model\" visibility=\"public\" xmi:type=\"uml:Model\""));
	}
	
	@Test
	public void test_Build_Extension_XMI() throws Exception {
		String xmiActual = builder.build();
		assertThat(
				xmiActual,
				containsString("<xmi:Extension extender=\"Enterprise Architect\" extenderID=\"6.5\""));
	}
	

	private void assertAttributes(Map<String, Matcher<String>> map,
			NamedNodeMap attributes) {
		Set<Entry<String, Matcher<String>>> entrySet = map.entrySet();
		for (Entry<String, Matcher<String>> entry : entrySet) {
			Node namedItem = attributes.getNamedItem(entry.getKey());
			assertThat(namedItem.getNodeValue(), entry.getValue());
		}
	}

	private void assertLifeline(String name, String xmiActual, int index)
			throws ParserConfigurationException, SAXException, IOException {
		//<lifeline xmi:type="uml:Lifeline" xmi:id="EAID_915A0338_3D6D_47bd_B144_45750FDDA54A" name="Object1" visibility="public" represents="EAID_AT000000_3D6D_47bd_B144_45750FDDA54A"/>
		Map<String, Matcher<String>> map = new HashMap<String, Matcher<String>>();
		map.put("xmi:type", equalTo("uml:Lifeline"));
		map.put("name", equalTo(name));
		map.put("xmi:id", not(""));
		map.put("represents", not(""));
		assertTagWithAttributes(xmiActual, "lifeline", index, map);
	}
	
	private void assertMessage(String xmiActual, int index, String nameMethod)
			throws ParserConfigurationException, SAXException, IOException {
		Map<String, Matcher<String>> map = new HashMap<String, Matcher<String>>();
		map.put("xmi:type", equalTo("uml:Message"));
		map.put("name", equalTo(nameMethod));
		map.put("messageSort", equalTo("synchCall"));
		map.put("messageKind", equalTo("complete"));
		map.put("xmi:id", not(""));
		map.put("sendEvent", not(""));
		map.put("receiveEvent", not(""));
		assertTagWithAttributes(xmiActual, "message", index, map);
	}

	private void assertMessageReply(String xmiActual, int index)
			throws ParserConfigurationException, SAXException, IOException {
		Map<String, Matcher<String>> map = new HashMap<String, Matcher<String>>();
		map.put("xmi:type", equalTo("uml:Message"));
		map.put("messageSort", equalTo("reply"));
		map.put("messageKind", equalTo("complete"));
		map.put("xmi:id", not(""));
		map.put("sendEvent", not(""));
		map.put("receiveEvent", not(""));
		assertTagWithAttributes(xmiActual, "message", index, map);
	}

	private void assertTagWithAttributes(String xmiActual, String tagname,
			int index, Map<String, Matcher<String>> map)
			throws ParserConfigurationException, SAXException, IOException {
		Document parse = parseDocument(xmiActual);
		NodeList childNodes = parse.getElementsByTagName(tagname);
		assertNotNull(childNodes);
		assertTrue(childNodes.getLength() > index);
		NamedNodeMap attributes = childNodes.item(index).getAttributes();

		assertAttributes(map, attributes);
	}

	private Document parseDocument(String xmiActual)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document parse = documentBuilder.parse(new InputSource(
				new StringReader(xmiActual)));
		return parse;
	}

}
