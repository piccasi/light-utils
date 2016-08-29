package org.light4j.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.StringTokenizer;

import org.light4j.utils.date.DateUtils;
import org.light4j.utils.random.RandomUtils;

/**
 * 文件工具类
 * 
 * @author longjiazuo
 */
public class FileUtils {
	private static final String FOLDER_SEPARATOR = "/";
	private static final char EXTENSION_SEPARATOR = '.';
	
	/**
	 * 遍历文件夹中文件
	 * @param filepath     文件路径
	 * @return 返回file［］ 数组
	 */
	public static File[] getFileList(String filepath) {
		File d = null;
		File list[] = null;
		/** 建立当前目录中文件的File对象 **/
		try {
			d = new File(filepath);
			if (d.exists()) {
				list = d.listFiles();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** 取得代表目录中所有文件的File对象数组 **/
		return list;
	}
	
	/**
	 * 读取文本文件内容
	 * @param filePathAndName  带有完整绝对路径的文件名
	 * @param encoding         文本文件打开的编码方式
	 * @return                 返回文本文件的内容
	 */
	public static String readTxt(String filePathAndName, String encoding) throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data);
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
			if (st != null && st.length() > 1)
				st = st.substring(0, st.length() - 1);
		} catch (IOException es) {
			st = "";
		}
		return st;
	}
	
	/**
	 * 新建目录
	 * @param folderPath  目录
	 * @return            返回目录创建后的路径
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 * @param folderPath 准备要在本级目录下创建新目录的目录路径例如 c:myf
	 * @param paths      无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return           返回创建文件后的路径
	 */
	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txts;
	}
	
	/**
	 * @desc:判断指定路径是否存在，如果不存在，根据参数决定是否新建
	 *
	 * @param filePath
	 * 			指定的文件路径
	 * @param isNew
	 * 			true：新建、false：不新建
	 * @return 存在返回TRUE，不存在返回FALSE
	 */
	public static boolean isExist(String filePath,boolean isNew){
		File file = new File(filePath);
		if(!file.exists() && isNew){    
			return file.mkdirs();    //新建文件路径
		}
		return false;
	}
	
	/**
	 * 新建文件
	 * @param filePathAndName 文本文件完整绝对路径及文件名
	 * @param fileContent     文本文件内容
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 有编码方式的文件创建
	 * @param filePathAndName 文本文件完整绝对路径及文件名
	 * @param fileContent     文本文件内容
	 * @param encoding  编码方式 例如 GBK 或者 UTF-8
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent, String encoding) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * @param filePathAndName 文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public static boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bea;
	}
	
	/**
	 * 删除文件夹以及下面的所有文件
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static void delFolder(String folderPath) {
		try {
			/**删除完里面所有内容**/
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			/**删除空文件夹**/
			myFilePath.delete(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) { return bea;}
		if (!file.isDirectory()) { return bea;}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) { temp.delete(); }
			if (temp.isDirectory()) {
				/**先删除文件夹里面的文件**/
				delAllFile(path + "/" + tempList[i]);
				/**再删除空文件**/
				delFolder(path + "/" + tempList[i]);
				bea = true;
			}
		}
		return bea;
	}
	
	/**
	 * 获取文件名，构建结构为 prefix + yyyyMMddHH24mmss + 10位随机数 + suffix + .type
	 *
	 * @param type
	 * 				文件类型
	 * @param prefix
	 * 				前缀
	 * @param suffix
	 * 				后缀
	 * @return
	 */
	public static String getFileName(String type,String prefix,String suffix){
		String date = DateUtils.getCurrentTime("yyyyMMddHH24mmss");   //当前时间
		String random = RandomUtils.generateNumberString(10);   //10位随机数
		
		//返回文件名  
		return prefix + date + random + suffix + "." + type;
	}
	
	/**
	 * 获取文件名，文件名构成:当前时间 + 10位随机数 + .type
	 *
	 * @param type
	 * 				文件类型
	 * @return
	 */
	public static String getFileName(String type){
		return getFileName(type, "", "");
	}
	
	/**
	 * 获取文件名，文件构成：当前时间 + 10位随机数
	 *
	 * @return
	 */
	public static String getFileName(){
		String date = DateUtils.getCurrentTime("yyyyMMddHH24mmss");   //当前时间
		String random = RandomUtils.generateNumberString(10);   //10位随机数
		
		//返回文件名  
		return date + random;
	}
	
	/**
	 * 获取指定文件的大小
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("resource")
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
		}
		return size;
	}
	
	/**
	 * 删除所有文件，包括文件夹
	 * 
	 * @param dirpath
	 */
    public void deleteAll(String dirpath) {  
    	 File path = new File(dirpath);  
         try {  
             if (!path.exists())  
                 return;// 目录不存在退出   
             if (path.isFile()) // 如果是文件删除   
             {  
                 path.delete();  
                 return;  
             }  
             File[] files = path.listFiles();// 如果目录中有文件递归删除文件   
             for (int i = 0; i < files.length; i++) {  
                 deleteAll(files[i].getAbsolutePath());  
             }  
             path.delete();  

         } catch (Exception e) {  
             e.printStackTrace();  
         }   
    }
    
    /**
     * 复制文件或者文件夹
     * 
     * @param inputFile
     * 						源文件
     * @param outputFile
     * 						目的文件
     * @param isOverWrite
     * 						是否覆盖文件
     * @throws java.io.IOException
     */
    public static void copy(File inputFile, File outputFile, boolean isOverWrite)
			throws IOException {
		if (!inputFile.exists()) {
			throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
		}
		copyPri(inputFile, outputFile, isOverWrite);
	}
    
    /**
     * 复制文件或者文件夹
     * 
     * @param inputFile
     * 						源文件
     * @param outputFile
     * 						目的文件
     * @param isOverWrite
     * 						是否覆盖文件
     * @throws java.io.IOException
     */
    private static void copyPri(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
		if (inputFile.isFile()) {		//文件
			copySimpleFile(inputFile, outputFile, isOverWrite);
		} else {
			if (!outputFile.exists()) {		//文件夹	
				outputFile.mkdirs();
			}
			// 循环子文件夹
			for (File child : inputFile.listFiles()) {
				copy(child, new File(outputFile.getPath() + "/" + child.getName()), isOverWrite);
			}
		}
	}
    
    /**
     * 复制单个文件
     * 
     * @param inputFile
     * 						源文件
     * @param outputFile
     * 						目的文件
     * @param isOverWrite
     * 						是否覆盖
     * @throws java.io.IOException
     */
    private static void copySimpleFile(File inputFile, File outputFile,
			boolean isOverWrite) throws IOException {
		if (outputFile.exists()) {
			if (isOverWrite) {		//可以覆盖
				if (!outputFile.delete()) {
					throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
				}
			} else {
				// 不允许覆盖
				return;
			}
		}
		InputStream in = new FileInputStream(inputFile);
		OutputStream out = new FileOutputStream(outputFile);
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		in.close();
		out.close();
	}
    

	/**
	 * 复制单个文件
	 * @param oldPathFile  准备复制的文件源
	 * @param newPathFile 拷贝到新绝对路径带文件名
	 * @return
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPathFile);
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * @param oldPath  准备拷贝的目录
	 * @param newPath  指定绝对路径的新目录
	 * @return
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			/**如果文件夹不存在 则建立新文件**/
			new File(newPath).mkdirs(); 
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				/**如果是子文件**/
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * 移动文件
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

    
    /**
     * 获取文件的MD5
     * 
     * @param file
     * 				文件
     * @return
     */
	public static String getFileMD5(File file){
		if (!file.exists() || !file.isFile()) {  
            return null;  
        }  
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];  
        int len;  
        try {  
            digest = MessageDigest.getInstance("MD5");  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);  
	}
	
	/**
	 * 获取文件的后缀
	 * 
	 * @param file
	 * 				文件
	 * @return
	 */
	public static String getFileSuffix(String file) {
		if (file == null) {
			return null;
		}
		int extIndex = file.lastIndexOf(EXTENSION_SEPARATOR);
		if (extIndex == -1) {
			return null;
		}
		int folderIndex = file.lastIndexOf(FOLDER_SEPARATOR);
		if (folderIndex > extIndex) {
			return null;
		}
		return file.substring(extIndex + 1);
	}
	
	/**
	 * 文件重命名
	 * 
	 * @param oldPath
	 * 					老文件
	 * @param newPath
	 * 					新文件
	 */
    public boolean renameDir(String oldPath, String newPath) {  
        File oldFile = new File(oldPath);// 文件或目录   
        File newFile = new File(newPath);// 文件或目录   
        
        return oldFile.renameTo(newFile);// 重命名   
    }
}
