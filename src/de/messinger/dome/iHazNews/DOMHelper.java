package de.messinger.dome.iHazNews;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMHelper {

	private DOMHelper() {
	}

	public static Node getChild(Node node, String tag) {
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node item = nodes.item(i);
			if (item.getNodeName().equals(tag))
				return item;
		}
		return null;
	}

	public static Node getItemByChildTextValue(NodeList nodes, String childTag,
			String value) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node item = nodes.item(i);
			if (getChild(item, childTag).getTextContent().equals(value))
				return item;
		}
		return null;
	}

}
