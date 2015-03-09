package org.saburto.seqxmi.gen.output;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.saburto.seqxmi.gen.input.InvocationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMIBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XMIBuilder.class);
	
	private static final String XMI_NS = "http://schema.omg.org/spec/XMI/2.1";
	private static final String UML_NS = "http://schema.omg.org/spec/UML/2.1";
	private Document doc;
	private Element rootElement;
	private Element model;
	private Element packageElement;
	private Element interactionElement;
	private Element extension;
	private Map<String, Element> lifelines = new HashMap<String, Element>();

	public XMIBuilder() {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			
			addRoot();
			addModel();
			addExtension();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addExtension() {
		//<xmi:Extension extender="Enterprise Architect" extenderID="6.5">

		extension = doc.createElementNS(XMI_NS, "xmi:Extension");
		extension.setAttribute("extender", "Enterprise Architect");
		extension.setAttribute("extenderID", "6.5");
		
		extension.appendChild(doc.createElement("connectors"));
		extension.appendChild(doc.createElement("diagrams"));
	}

	void addRoot() {
		rootElement = doc.createElementNS(XMI_NS, "xmi:XMI");
		//xmlns:uml="http://schema.omg.org/spec/UML/2.1"
		rootElement.setAttribute("xmlns:uml", UML_NS);
		rootElement.setAttributeNS(XMI_NS, "xmi:version", "2.1");
		
		doc.appendChild(rootElement);
	}

	public String build() {
		
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new StringWriter());
			transformer.transform(source, result);

			String string = result.getWriter().toString();
			LOGGER.info(string);
			return string;
		} catch (Exception e) {
		}
		
		return "";
	}

	void addModel() {
		//<uml:Model xmi:type="uml:Model" name="EA_Model" visibility="public">
		model = doc.createElementNS(UML_NS, "uml:Model");
		model.setAttributeNS(XMI_NS, "xmi:type", "uml:Model");
		model.setAttribute("name", "EA_Model");
		addAttributeVisible(model);
		
		
		rootElement.appendChild(model);
	}

	private void addAttributeVisible(Element element) {
		element.setAttribute("visibility", "public");
	}

	public XMIBuilder setPackage(String name) {
		//<packagedElement xmi:type="uml:Package" xmi:id="EAPK_07B0B9F0_2F3A_4a39_A2E9_3300E2D409B3" name="Package1" visibility="public">
		createElementModel(name);
		
		//<packagedElement xmi:type="uml:Collaboration" xmi:id="EAID_CB000000_9F0_2F3A_4a39_A2E9_3300E2D409B" name="EA_Collaboration1" visibility="public">
		
		Element packageCollaboration = createElementColaboration();
		
		//<ownedBehavior xmi:type="uml:Interaction" xmi:id="EAID_IN000000_9F0_2F3A_4a39_A2E9_3300E2D409B" name="EA_Interaction1" visibility="public">
		createElementInteraction(packageCollaboration);
		
		
		return this;
	}

	private void createElementInteraction(Element packageCollaboration) {
		interactionElement = doc.createElement("ownedBehavior");
		interactionElement.setAttributeNS(XMI_NS, "xmi:type", "uml:Interaction");
		interactionElement.setAttributeNS(XMI_NS, "xmi:id", UUID.randomUUID().toString());
		interactionElement.setAttribute("name", "EA_Interaction1");
		addAttributeVisible(interactionElement);
		
		packageCollaboration.appendChild(interactionElement);
	}

	private Element createElementColaboration() {
		Element packageCollaboration = doc.createElement("packagedElement");
		packageCollaboration.setAttributeNS(XMI_NS, "xmi:type", "uml:Collaboration");
		packageCollaboration.setAttributeNS(XMI_NS, "xmi:id", UUID.randomUUID().toString());
		packageCollaboration.setAttribute("name", "EA_Collaboration1");
		addAttributeVisible(packageCollaboration);
		
		packageElement.appendChild(packageCollaboration);
		return packageCollaboration;
	}

	private void createElementModel(String name) {
		packageElement = doc.createElement("packagedElement");
		packageElement.setAttributeNS(XMI_NS, "xmi:type", "uml:Package");
		packageElement.setAttribute("name", name);
		addAttributeVisible(packageElement);
		packageElement.setAttributeNS(XMI_NS, "xmi:id", UUID.randomUUID().toString());
		
		model.appendChild(packageElement);
	}
	
	

	public XMIBuilder addLifeline(String name) {
		addLifelineAndReturn(name);
		return this;
	}

	private Element addLifelineAndReturn(String name) {
		
		///if already exits exit this method
		if(lifelines.containsKey(name)){
			return lifelines.get(name);
		}
		
		Node parentNode = interactionElement.getParentNode();
		
		//<ownedAttribute xmi:type="uml:Property" xmi:id="EAID_AT000000_3D6D_47bd_B144_45750FDDA54A"/>
		Element property = doc.createElement("ownedAttribute");
		property.setAttributeNS(XMI_NS, "xmi:type", "uml:Property");
		String propertyId = UUID.randomUUID().toString();
		property.setAttributeNS(XMI_NS, "xmi:id", propertyId);
		
		
		parentNode.appendChild(property);
		
		//<lifeline xmi:type="uml:Lifeline" xmi:id="EAID_915A0338_3D6D_47bd_B144_45750FDDA54A" name="Object1" visibility="public" represents="EAID_AT000000_3D6D_47bd_B144_45750FDDA54A"/>
		Element lifeline = doc.createElement("lifeline");
		lifeline.setAttributeNS(XMI_NS, "xmi:type", "uml:Lifeline");
		lifeline.setAttribute("name", name);
		lifeline.setAttribute("represents", propertyId);
		addAttributeVisible(lifeline);
		lifeline.setAttributeNS(XMI_NS, "xmi:id", UUID.randomUUID().toString());
		
		interactionElement.appendChild(lifeline);
		
		lifelines.put(name, lifeline);
		return lifeline;
	}
	
	private Element getLifeLine(String from) {
		return addLifelineAndReturn(from);
	}

	public XMIBuilder addMessage(String from, InvocationInfo to) {
		//add
		Element fromElement = getLifeLine(from);
		Element toElement = getLifeLine(to.getType());
		
		
		addMessages(to, fromElement, toElement);
		
		return this;
	}

	private void addMessages(InvocationInfo to, Element fromElement,
			Element toElement) {
		addMessageCall(to, fromElement, toElement);
		addMessageReply(to, fromElement, toElement);
	}

	private void addMessageCall(InvocationInfo to, Element fromElement,	Element toElement) {
		addMessage(to, fromElement, toElement, false);
	}
	
	private void addMessageReply(InvocationInfo to, Element fromElement,	Element toElement) {
		addMessage(to, toElement, fromElement,  true);
	}

	private void addMessage(InvocationInfo to, Element fromElement,
			Element toElement, boolean reply) {
		//<fragment xmi:type="uml:OccurrenceSpecification" xmi:id="EAID_FR000000_3D6D_47bd_B144_45750FDDA54A" covered="EAID_915A0338_3D6D_47bd_B144_45750FDDA54A"/>
		//<fragment xmi:type="uml:OccurrenceSpecification" xmi:id="EAID_FR000000_5F1B_43d5_896E_0829E71FEBFD" covered="EAID_B3EED042_5F1B_43d5_896E_0829E71FEBFD"/>
		Element fragmentFrom = addFragment(fromElement);
		Element fragmentTo = addFragment(toElement);
		
		//<message xmi:type="uml:Message" xmi:id="EAID_4AC126E4_E3B7_4289_BF00_DADABCAE693E" name="Meotod1" messageKind="complete" messageSort="synchCall" sendEvent="EAID_FR000000_3D6D_47bd_B144_45750FDDA54A" receiveEvent="EAID_FR000000_5F1B_43d5_896E_0829E71FEBFD"/>
		
		//<message xmi:type="uml:Message" xmi:id="EAID_BB4DB84A_5727_4474_90D3_76411716FC71" messageKind="complete" messageSort="reply" sendEvent="EAID_FR000003_5F1B_43d5_896E_0829E71FEBFD" receiveEvent="EAID_FR000003_3D6D_47bd_B144_45750FDDA54A"/>
		Element message = doc.createElement("message");
		message.setAttributeNS(XMI_NS, "xmi:type", "uml:Message");
		message.setAttribute("name", reply ? to.getReturnType() : to.getName());
		message.setAttribute("messageKind", "complete");
		message.setAttribute("messageSort", reply ? "reply" : "synchCall");
		message.setAttribute("sendEvent", getXMIId(fragmentFrom));
		message.setAttribute("receiveEvent", getXMIId(fragmentTo));
		message.setAttributeNS(XMI_NS, "xmi:id", UUID.randomUUID().toString());
		
		
		
		interactionElement.appendChild(message);
	}

	private String getXMIId(Element element) {
		return element.getAttributeNS(XMI_NS, "id");
	}

	private Element addFragment(Element elementLifeline) {
		Element fragment = doc.createElement("fragment");
		fragment.setAttributeNS(XMI_NS, "xmi:type", "uml:OccurrenceSpecification");
		fragment.setAttribute("covered", getXMIId(elementLifeline));
		fragment.setAttributeNS(XMI_NS, "xmi:id", UUID.randomUUID().toString());
		
		interactionElement.appendChild(fragment);
		
		return fragment;
	}

	
}
