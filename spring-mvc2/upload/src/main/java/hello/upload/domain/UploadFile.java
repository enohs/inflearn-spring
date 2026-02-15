package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

  // 업로드 되는 파일 이름과 저장될 때의 이름을 따로 설정해서 동일한 이름의 파일명으로 업로드 됐을 때 중복을 방지함
  private String uploadFileName;
  private String storeFileName;

  public UploadFile(String uploadFileName, String storeFileName) {
    this.uploadFileName = uploadFileName;
    this.storeFileName = storeFileName;
  }
}
