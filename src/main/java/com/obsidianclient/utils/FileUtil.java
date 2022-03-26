/**
 * This file is part of Obsidian Client.
 * Copyright (C) 2022  Alexander Richter
 *
 * Obsidian Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Obsidian Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Obsidian Client.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.obsidianclient.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;

/**
 * Simple utility for saving contents of a class into a file.
 */
public class FileUtil {

	/**
	 * Saves all fields from a class with the @Savable Annotation into a file.
	 * INFO: The fields must be marked with @Savable!
	 * INFO: This only works with primitive data types!
	 */
	public static void saveObjectToFile(Object obj, File file) throws IOException {
		
		//The content of the file that is going to be written:
		List<String> fileContent = new ArrayList<String>();

		//Looping through the class of the object and all superclasses:
		Class<?> currentClazz = obj.getClass();
		while (currentClazz != null) {

			//Getting the content out of the class (by looping through it):
			Field[] fields = currentClazz.getDeclaredFields();
			for(Field field : fields) {
				if (field.isAnnotationPresent(Savable.class)) {

				    //Making it accessible:
					boolean accessibleBefore = field.isAccessible();
					field.setAccessible(true);

					try {
						fileContent.add(field.getName() + "=" + field.get(obj) + "\n");
					} catch (IllegalAccessException e) {
						LogManager.getLogger().error("[Obsidian Client - FileUtil] Can't access field: " + field.getName() + "!");
						e.printStackTrace();
					}

					//Restoring the old accessibility:
					field.setAccessible(accessibleBefore);
				}
			}

			currentClazz = currentClazz.getSuperclass();
		}
		
		//Creating the file, if it does not exist:
	    if (!file.exists()) {
	    	if (!file.createNewFile()) {
	      		LogManager.getLogger().error("[Obsidian Client - FileUtil] Can't create file: " + file.getAbsolutePath());
		  	}
	    }

	    //Writing the file:
	    FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    for (String s : fileContent) {
	    	bufferedWriter.write(s);
	    }
	    bufferedWriter.close();
	    fileWriter.close();
	    
	}

	/**
	 * Fills all fields from a class with @Saveable from a file:
	 * INFO: The fields must be marked with @Savable!
	 * INFO: This only works with primitive data types!
	 */
	public static void fillObjectFromFile(Object obj, File file) throws FileNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        //The content of the file:
        List<String> content = new ArrayList<String>();

        //Reading the lines from the file into the array:
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            content.add(reader.nextLine());
        }

        //Filling the methods:
        for (String s : content) {

            //Getting Information:
            String name = s.split("=")[0];
            String value = s.split("=")[1];

            //Checking the Class of the Object and all Superclasses:
			Class<?> currentClazz = obj.getClass();
			while (currentClazz != null) {

				//Finding the right Field:
				for (Field currentField : currentClazz.getDeclaredFields()) {
					if (currentField.getName().equals(name)) {

						//Making it accessible:
						boolean accessibleBefore = currentField.isAccessible();
						currentField.setAccessible(true);

						//Finally, filling the fields with the previously loaded values (different for each data type):
						if (currentField.getType() == boolean.class) currentField.set(obj, Boolean.parseBoolean(value));
						if (currentField.getType() == int.class) currentField.set(obj, Integer.parseInt(value));
						if (currentField.getType() == double.class) currentField.set(obj, Double.parseDouble(value));
						if (currentField.getType() == long.class) currentField.set(obj, Long.parseLong(value));
						if (currentField.getType() == byte.class) currentField.set(obj, Byte.parseByte(value));
						if (currentField.getType() == short.class) currentField.set(obj, Short.parseShort(value));
						if (currentField.getType() == float.class) currentField.set(obj, Float.parseFloat(value));
						if (currentField.getType() == String.class) currentField.set(obj, value);

						//Restoring the old accessibility:
						currentField.setAccessible(accessibleBefore);

					}
				}

				currentClazz = currentClazz.getSuperclass();
			}

        }

    }

	/**
	 * Extracts the license together with the third-party notice.
	 * @param folder The folder to extract into.
	 * @throws IOException If something goes wrong.
	 */
	public static void extractLicenseAnd3rdParty(File folder) throws IOException {

		//Creating File-Objects pointing to the target files:
		File licenseFile = new File(folder, "LICENSE");
		File thirdPartyFile = new File(folder, "THIRD_PARTY");

		if (!licenseFile.exists() || !thirdPartyFile.exists()) {

			//Creating Streams pointing to the target files:
			InputStream licenseStream = FileUtil.class.getResourceAsStream("/LICENSE");
			InputStream thirdPartyStream = FileUtil.class.getResourceAsStream("/THIRD_PARTY");
			FileOutputStream licenseOutputStream = new FileOutputStream(licenseFile);
			FileOutputStream thirdPartyOutputStream = new FileOutputStream(thirdPartyFile);

			//Copying the content from the InputStreams into the OutputStreams:
			while (licenseStream.available() > 0) {
				licenseOutputStream.write(licenseStream.read());
			}
			while (thirdPartyStream.available() > 0) {
				thirdPartyOutputStream.write(thirdPartyStream.read());
			}

			//Closing the streams:
			licenseStream.close();
			thirdPartyStream.close();
			licenseOutputStream.close();
			thirdPartyOutputStream.close();

		}

	}
	
}
