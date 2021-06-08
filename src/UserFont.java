package TextPrinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class UserFont {

	
	public static void main(String[] args) {
//		try {
//			CreatingFontFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try {
			Unzip(zipFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void CreatingFontFile() throws IOException {
		String filename = System.getProperty("user.dir") + "\\data\\v3+";
		File[] fs = new File(filename).listFiles();
		System.out.println(fs[0] + "");
        
        FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") +
				"\\data\\fonts\\userFont.zip");
        ZipOutputStream zout = new ZipOutputStream(fout);
        // Определение кодировки
//        zout.setEncoding("CP866");
            	
        // Создание объекта File object архивируемой директории
        File fileSource = new File(System.getProperty("user.dir") + "\\data\\v3+");
                   
        try {
			addDirectory(zout, fileSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
                   
        // Закрываем ZipOutputStream
        zout.close();
                   
        System.out.println("Zip файл создан!");
	}
	
	private static void addDirectory(ZipOutputStream zout, File fileSource) 
			throws Exception
	{
		File[] files = fileSource.listFiles();
		System.out.println("Добавление директории <" + fileSource.getName() + ">");
		for(int i = 0; i < files.length; i++) {
			// Если file является директорией, то рекурсивно вызываем 
			// метод addDirectory
			if(files[i].isDirectory()) {
				addDirectory(zout, files[i]);
				continue;
			}
			System.out.println("Добавление файла <" + files[i].getName() + ">");

			FileInputStream fis = new FileInputStream(files[i]);

			zout.putNextEntry(new ZipEntry(files[i].getPath()));

			byte[] buffer = new byte[4048];
			int length;
			while((length = fis.read(buffer)) > 0)
				zout.write(buffer, 0, length);
			// Закрываем ZipOutputStream и InputStream
			zout.closeEntry();
			fis.close();
		}
	}
	
	
	
	
	private final static String zipDir  = System.getProperty("user.dir") + "\\data\\datafonts";
	private final static String zipFile = System.getProperty("user.dir") + "\\data\\fonts\\userFont.zip";

	private final static String SLASH_BACK     = "/";
	private final String CHARSET_CP866  = "CP866";

	
	private static void createDir(final String dir)
	{
	    File file = new File(dir);
	    if (!file.exists())
	        file.mkdirs();
	}
	private static void createFolder(final String dirName)
	{
	    if (dirName.endsWith(SLASH_BACK))
	        createDir(dirName.substring(0, dirName.length() - 1));
	}
	private static void checkFolder(final String file_path)
	{
	    if (!file_path.endsWith(SLASH_BACK) && file_path.contains(SLASH_BACK)) {
	        String dir = file_path.substring(0, file_path.lastIndexOf(SLASH_BACK));
	        createDir(dir);
	    }
	}
	private static void Unzip(final String zipDir) throws Exception
	{
	    ZipFile zipFile = new ZipFile(zipDir);
	    Enumeration<?> entries = zipFile.entries();
	    while (entries.hasMoreElements()) {
	        ZipEntry entry = (ZipEntry) entries.nextElement();
	        String entryName = entry.getName();
	        if (entryName.endsWith(SLASH_BACK)) {
	            System.out.println("Создание директории <" + entryName + ">");
	            createFolder (entryName);
	            continue;
	        } else
	            checkFolder(entryName);
	        System.out.println("Чтение файла <" + entryName + ">");
	        InputStream  fis = (InputStream) zipFile.getInputStream(entry);
	       		
	        FileOutputStream fos = new FileOutputStream(entryName);
	        byte[] buffer = new byte[fis.available()];
	        // Считываем буфер
	        fis.read(buffer, 0, buffer.length);
	        // Записываем из буфера в файл
	        fos.write(buffer, 0, buffer.length);
	        fis.close();
	        fos.close();
	    }
	    zipFile.close() ;      
	    System.out.println("Zip файл разархивирован!");
	}
}
