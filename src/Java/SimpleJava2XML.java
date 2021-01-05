package Java;

import Java.Person;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileWriter;

public class SimpleJava2XML {


    public static void main(String[] args) {
        try {

            XmlMapper xmlMapper = new XmlMapper();//import that shit
            xmlMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

            Person henning = new Person("10102020-2021");

            henning.setCPR("123456-2222");
            henning.setFnavn("Henning");
            henning.setEnavn("Johari");
            henning.setTlfnummer(12312313);

            String xmlstring = xmlMapper.writeValueAsString(henning);
            System.out.println(xmlstring);

            File xmlOutput = new File("henning.xml");
            FileWriter fileWriter = new FileWriter(xmlOutput);
            fileWriter.write(xmlstring);


            Person daniel = new Person("123456-1234");

            daniel.setCPR("123456-1234");
            daniel.setFnavn("Daniel");
            daniel.setEnavn("Johari");
            daniel.setTlfnummer(22286666);


            String xmlstring2 = xmlMapper.writeValueAsString(daniel);

            System.out.println(xmlstring2);

            File xmlOutput2 = new File("daniel.xml");
            FileWriter fileWriter2 = new FileWriter(xmlOutput2);
            fileWriter2.write(xmlstring2);

            fileWriter.close();
            fileWriter2.close();


            //write this shit down into an XML file:

        } catch (Exception e) {
            //https://www.baeldung.com/jackson-jsonmappingexception use this to locate problems with things not being where
            //they should be
            e.printStackTrace();
        }

    }
}
