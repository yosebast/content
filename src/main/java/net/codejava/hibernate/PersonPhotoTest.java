package net.codejava.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Test Hibernate binary data mapping with java.sql.Blob type.
 * @author www.codejava.net
 *
 */
public class PersonPhotoTest {
	private static ServiceRegistry serviceRegistry;
	private static Session session;
	
	public static void main(String[] args) throws IOException, SQLException {
		//initSession();
		
		//String photoFilePathToRead = "C:/Users/vmelo/Desktop/ProyectoCompraVenta/pulserapiel.jpg";
		//savePersonWithPhoto(photoFilePathToRead);
		
		//endSession();
		
		initSession();
		
		int personId = 1;
		String photoFilePathToSave = "C:/Users/vmelo/Desktop/pulserapiel.jpg";
		readPhotoOfPerson(personId, photoFilePathToSave);
		
		endSession();
	}
	
	private static void savePersonWithPhoto(String photoFilePath) throws IOException, SQLException {
		Person person = new Person("Peter");
		File file = new File(photoFilePath);
		FileInputStream inputStream = new FileInputStream(file);
		Blob blob = Hibernate.getLobCreator(session)
							.createBlob(inputStream, file.length());
		person.setPhoto(blob);
		session.save(person);
		blob.free();
	}
	
	private static void readPhotoOfPerson(int personId, String photoFilePath) throws IOException, SQLException {
		Person person = (Person) session.get(Person.class, personId);
		Blob blob = person.getPhoto();
		byte[] blobBytes = blob.getBytes(1, (int) blob.length());
		saveBytesToFile(photoFilePath, blobBytes);
		blob.free();
	}
	
	private static void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(filePath);
		outputStream.write(fileBytes);
		outputStream.close();
	}
	
	private static void initSession() {
		Configuration configuration = new Configuration().configure();
		serviceRegistry	= new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		session.beginTransaction();
	}
	
	private static void endSession() {
		session.getTransaction().commit();
		session.close();
		
		StandardServiceRegistryBuilder.destroy(serviceRegistry);		
	}

}
