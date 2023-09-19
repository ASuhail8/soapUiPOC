package sampleTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlProcessing {

    public static void xmlProcessing() {
        try {
            // Parse the XML file
            File xmlFile = new File("bookstore.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);

            // Locate the book element with the title "Web Development Basics"
            NodeList bookList = doc.getElementsByTagName("book");
            for (int i = 0; i < bookList.getLength(); i++) {
                Element book = (Element) bookList.item(i);
                Element titleElement = (Element) book.getElementsByTagName("title").item(0);
                String title = titleElement.getTextContent();

                if (title.equals("Java Programming")) {
                    // Update the price
                    Element priceElement = (Element) book.getElementsByTagName("Access").item(0);
                    priceElement.setTextContent(launchGoogle()); // Set the new price
/*
                    //remove child
                    book.removeChild(priceElement);

                    // Create and set a new price element
                    Element newPriceElement = doc.createElement("price");
                    newPriceElement.setTextContent("5.99");
                    book.appendChild(newPriceElement); */


                    // Save the changes to the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();

                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(xmlFile);
                    transformer.transform(source, result);

                    System.out.println("Price updated successfully.");
                    break; // Exit the loop since we found and updated the book.
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebDriver driver;

    public static String launchGoogle() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(2000);
        driver.quit();
        return "ya29.A0ARrdaM9t9aljiD0iEA2PhEk67gBTa7NTmcBm2EQxNytzXH_R99RIhfCe9qnn7oeRZd2UzOwIKfNYQNjeMJn1PTttnZxPbyCgReh9gRwVCekDNFov9EItb9MsKL4Evqvi5KrPFmN5VWXGHD9Jm-UMeJAYdC-o";
    }

}
