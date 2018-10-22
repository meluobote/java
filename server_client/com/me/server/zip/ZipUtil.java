package com.me.server.zip;
/**
 * �����ࣺ ѹ����ѹ��
 * @author gehaixiang
 *
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	private static final String DEFAULTZIPENTRYNAME = "default.xml";

	public ZipUtil() {

	}

	/**
	 * <p>
	 * Discription: ������������ѹ��, �ѽ��ת�����ַ������浽map��
	 * </p>
	 * 
	 * @param is
	 *            ѹ��������
	 * @return map ����ÿ��zipEntryѹ���ļ�����
	 * @throws Exception
	 */
	public Map decompressToString(InputStream is) throws Exception {
		if (is == null) {
			throw new Exception("������Ϊ��ֵ");
		}
		Map map = new HashMap<>();
		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry zipEntry = zis.getNextEntry();
		if (zipEntry != null) {
			String zipEntryName = DEFAULTZIPENTRYNAME;
			if (zipEntry.getName() != null && !"".equals(zipEntry.getName())) {
				zipEntryName = zipEntry.getName();
			}
			map.put(zipEntryName, parseContentToString(zis));
		}

		zis.close();
		return map;
	}

	/**
	 * <p>
	 * Discription: ��ѹ���ļ����н�ѹ�� �ѽ��ת�����ַ���������map��
	 * </p>
	 * 
	 * @param filePath
	 *            ѹ���ļ���·��
	 * @return map ����ÿ��zipEntryѹ���ļ�������
	 * @throws Exception
	 * 
	 */
	public Map decompressToString(String filePath) throws Exception {
		if (filePath == null) {
			throw new Exception("������Ϊ��ֵ");
		}
		return decompressToString(new FileInputStream(filePath));
	}

	/**
	 * <p>
	 * Discription: ���ַ������ݣ�ѹ������д�������
	 * </p>
	 * 
	 * @param filePath
	 *            ����ļ�·��
	 * 
	 */
	public void compress(String filePath, String content) throws Exception {
		if (filePath == null) {
			throw new Exception("������Ϊ��ֵ");
		}
		compress(new FileOutputStream(filePath), content);
	}

	/**
	 * <p>
	 * Discription: ���ַ������ݣ� ѹ������д�������</pk>
	 * 
	 * @param os
	 *            �����
	 *
	 */
	public void compress(OutputStream os, String content) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(os);
		ZipEntry zipEntry = new ZipEntry(DEFAULTZIPENTRYNAME);
		zos.putNextEntry(zipEntry);
		BufferedOutputStream bos = new BufferedOutputStream(zos);
		byte[] data = stringToByte(content);
		int offset = 0;
		int size = data.length;
		int len = 256;
		while (offset < size) {
			if (size - offset < len) {
				len = size - offset;
			}
			bos.write(data, offset, len);
			offset += len;

		}
		bos.flush();
		if (zos != null) {
			zos.closeEntry();
			zos.close();
		}
		if (bos != null) {
			bos.close();
		}
	}

	/**
	 * <p>
	 * Discription: ���ı�����ת����ѹ�����ֽ�����
	 * </p>
	 * 
	 * @param content
	 *            �ı�����
	 * @return
	 * @throws IOException
	 * 
	 */
	private byte[] getZippedContent(String content) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		ZipEntry zipEntry = new ZipEntry(DEFAULTZIPENTRYNAME);
		zos.putNextEntry(zipEntry);
		byte[] data = stringToByte(content);
		zos.write(data, 0, data.length);
		zos.flush();
		data = baos.toByteArray();
		if (zos != null) {
			zos.closeEntry();
			zos.close();
		}
		if (baos != null) {
			baos.close();
		}
		return data;
	}

	/**
	 * <p>
	 * Discription: ��ѹ������ת�����ֽ�����
	 * </p>
	 * 
	 * @param content
	 *            ѹ������
	 * @return
	 * @throws IOException
	 */
	private byte[] getZippedContent(InputStream content) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buf = new byte[512];
		int len = 0;
		while ((len = content.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		baos.flush();
		buf = baos.toByteArray();
		if (baos != null) {
			baos.close();
		}
		return buf;
	}

	/**
	 * <p>
	 * Discription: ��ѹ������ֽ������ѹ�� ���Ұ��ı����ݷ���map
	 * </p>
	 * 
	 * @param content
	 *            ѹ������ֽ�����
	 * @return
	 * @throws IOException
	 */
	private Map getUNZippedContent(byte[] content) throws IOException {
		Map map = new HashMap<>();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		ZipInputStream zis = new ZipInputStream(bais);
		ZipEntry zipEntry = zis.getNextEntry();
		if (zipEntry != null) {
			String zipEntryName = DEFAULTZIPENTRYNAME;
			if (zipEntry.getName() != null && !"".equals(zipEntry.getName())) {
				zipEntryName = zipEntry.getName();
			}

			map.put(zipEntryName, parseContentToString(zis));
		}
		if (zis != null) {
			zis.closeEntry();
			zis.close();
		}
		if (bais != null) {
			bais.close();
		}
		return map;
	}

	/**
	 * <p>
	 * Discription: ����zip��
	 * </p>
	 * 
	 * @param zis
	 *            zip������
	 * @return
	 * @throws IOException
	 */
	private String parseContentToString(InputStream zis) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(zis);
		byte[] buf = new byte[256];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		int len = 0;
		while ((len = bis.read(buf)) != -1) {
			bos.write(buf, 0, len);
		}
		bos.flush();
		buf = baos.toByteArray();
		if (baos != null) {
			baos.close();
		}
		if (bos != null) {
			bos.close();
		}
		if (bis != null) {
			bis.close();
		}
		return byteToString(buf);
	}

	/**
	 * <p>
	 * Discription: ���ֽ����鰴��GBK��ת�����ַ���
	 * </p>
	 * 
	 * @param data
	 *            �ֽ�����
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String byteToString(byte[] data) throws UnsupportedEncodingException {
		return new String(data, "GBK");
	}

	/**
	 * <p>
	 * Discription: ���ֽ����鰴�ַ���ת�����ַ���
	 * </p>
	 * 
	 * @param data
	 *            �ֽ�����
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String byteToString(byte[] data, String charset) throws UnsupportedEncodingException {
		return new String(data, charset);
	}

	/**
	 * <p>
	 * Discription: ���ַ������ַ���ת����
	 * </p>
	 * 
	 * @param data
	 *            �ֽ�����
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public byte[] stringToByte(String data, String charSet) throws UnsupportedEncodingException {
		return data.getBytes(charSet);
	}

	/**
	 * <p>
	 * Discription: ���ַ�����"GBK"ת����
	 * </p>
	 * 
	 * @param data
	 *            �ֽ�����
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public byte[] stringToByte(String data) throws UnsupportedEncodingException {
		return data.getBytes("GBK");
	}

	private List parseString(String data, String nodeName) {
		String reg = "</".concat(nodeName.toUpperCase()).concat(">");
		List list = new ArrayList<>();
		int bindex = data.indexOf("<".concat(nodeName.toUpperCase()));

		int eindex = data.lastIndexOf(reg);
		String commands = data.substring(bindex, eindex + 10);
		String[] command = commands.split(reg);
		for (int i = 0; i < command.length; i++) {
			list.add(command[i].concat(reg));
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
		String xml = "��ã���ӭ���٣���ã���ӭ���٣���ã���ӭ���٣���ã���ӭ���٣���ã���ӭ���٣���ã���ӭ���٣�";
		byte[] data = xml.getBytes("GBK");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		System.out.println(data.length);
		int offset = 0;
		int len = 256;
		while (offset < data.length) {
			if (data.length - offset < len) {
				len = data.length - offset;
			}
			bos.write(data, offset, len);
			offset += len;
		}
		bos.flush();
		bos.close();
		byte[] data1 = baos.toByteArray();
		baos.close();
		bos = new BufferedOutputStream(System.out);
		bos.write(data1);
		bos.flush();
		bos.close();
	}
}
