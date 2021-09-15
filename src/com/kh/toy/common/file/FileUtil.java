package com.kh.toy.common.file;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.taglibs.standard.tag.common.core.ParamParent;

import com.kh.toy.common.code.Config;
import com.kh.toy.common.code.ErrorCode;
import com.kh.toy.common.exception.HandlableException;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

public class FileUtil {

	//multipart 요청 도착 - > multipartParser를 사용해, 파일업로드 + 요청 파라미터 저장 + fileDTO 생성
	private static final int MAX_SIZE = 1024*1024*10;
	
	
	public MultiPartParams fileUpload(HttpServletRequest request){
		//첨에는 map으로 뿌릴꺼지만, 나중엔 multpart요청 객체를 만들어서 getparamter해서 사용하면 좋죠
		
		//뭐부터 할거냐면, 아직까지 한번도 multipartParser을 아직 안써봐서 써볼거빈다
		 Map<String,List> res = new HashMap<String, List>();
		 List<FileDTO> fileDTOs = new ArrayList<FileDTO>();
		 
		 try {
			MultipartParser parser = new MultipartParser(request, MAX_SIZE);
			parser.setEncoding("UTF-8");
			Part part = null;
			
			while((part=parser.readNextPart()) != null) {
				
				//is파일,is파람,getname으로 어케 파싱해주는지 확인
				System.out.println(part.isFile());  //boolean값
				System.out.println(part.isParam()); //boolean값
				System.out.println(part.getName()); 
				
				
				//input type=file요소가 존재하면, 파일첨부를 안해도 빈 filePart가 넘어옴
				//getFileName에서 null이 반환됨
				
				if(part.isFile()) { //file일때
				
				FilePart filePart = (FilePart) part;
				
					if(filePart.getFileName() !=null) {
						FileDTO fileDTO = createFileDTO(filePart);
						filePart.writeTo(new File(fileDTO.getSavePath()+fileDTO.getRenameFileName()));
						fileDTOs.add(fileDTO);
					}
				
				}else {  //file이 아니면 param으로 받아옴
					ParamPart paramPart = (ParamPart) part;
					setParameterMap(paramPart,res);
				}
			}
			res.put("com.kh.toy.files",fileDTOs);
		} catch (IOException e) {
			throw new HandlableException(ErrorCode.FAILED_FILE_UPLOAD_ERROR,e);
		}
		 return new MultiPartParams(res);
	}
	
	
	private String getSavePath() {
		
		//2.저장경로를 웹어플리케이션 외부로 지정 (외부경로 + 연웡일 형태로 작성)
		//config에 만들을거
		
		String subPath = getSubPath();
		String savePath = Config.UPLOAD_PATH.DESC + subPath;//저장경로
		
		File dir = new File(savePath);
		if(!dir.exists()) {
			 dir.mkdirs(); 
		}
		
		return savePath;
	}
	
	private String getSubPath() {
		Calendar today = Calendar.getInstance();
		int year= today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH)+1;
		int date = today.get(Calendar.DATE);
		
		return year + "\\" + month +"\\" + date + "\\";
		
	}
	
	
	
	private FileDTO createFileDTO(FilePart filePart) {
		FileDTO fileDTO = new FileDTO();
		String renameFileName = UUID.randomUUID().toString();
		String savePath = getSubPath();
		
		fileDTO.setOriginFileName(filePart.getFileName());
		fileDTO.setRenameFileName(renameFileName);
		fileDTO.setSavePath(savePath);
		
		return fileDTO;
	}
	
	private void setParameterMap(ParamPart paramPart, Map<String,List> res) throws UnsupportedEncodingException {
		if(res.containsKey(paramPart.getName())) { 
			//1. 해당 파라미터명으로 기존에 파라미터 값이 존재할 경우
			res.get(paramPart.getName()).add(paramPart.getStringValue());
		}else {
			//2. 해당 파라미터명으로 처음 파라미터값이 저장되는 경우
			List<String> param = new ArrayList<String>();
			param.add(paramPart.getStringValue());
			res.put(paramPart.getName(), param);
		}
	}
	
	
}
