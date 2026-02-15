package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemRepository itemRepository;
  private final FileStore fileStore;

  @GetMapping("/items/new")
  public String newItem(@ModelAttribute ItemForm form) {
    return "item-form";
  }

  @PostMapping("/items/new")
  public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {
    // 실제 파일은 스토리지에 저장
    UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
    List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

    // 데이터베이스에 저장
    // 데이터베이스에는 실제 파일이 아닌 파일의 저장 경로만 담아서 저장
    // 경로의 경우에도 fullPath가 아닌 상대경로만 담음 (전체 경로는 다른 데에 설정해두는 식)
    Item item = new Item();
    item.setItemName(form.getItemName());
    item.setAttachFile(attachFile);
    item.setImageFiles(storeImageFiles);
    itemRepository.save(item);

    redirectAttributes.addAttribute("itemId", item.getId());
    return "redirect:/items/{itemId}";
  }

  @GetMapping("/items/{id}")
  public String items(@PathVariable Long id, Model model) {
    Item item = itemRepository.findById(id);
    model.addAttribute("item", item);
    return "item-view";
  }

  // 이미지 반환해서 화면에 이미지 띄우기
  // 보안에 취약한 상태라 체크 로직을 더 넣을 필요는 있음
  @ResponseBody
  @GetMapping("/images/{filename}")
  public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
    // file:/User/shone/../qwer-qwer-qwer-qwer.png
    // 직접 파일 시스템에 접근해서 파일을 스트림으로 반환해줌
    return new UrlResource("file:" + fileStore.getFullPath(filename));
  }

  @GetMapping("/attach/{itemId}")
  public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
    Item item = itemRepository.findById(itemId);
    String storeFileName = item.getAttachFile().getStoreFileName();
    String uploadFileName = item.getAttachFile().getUploadFileName();

    // 여기까지 나온 결과를 반환해서 링크를 클릭하게 되면 다운로드가 아니라 파일 내용이 보임
    UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
    log.info("uploadFileName={}", uploadFileName);

    // 한글이나 다른 문자들은 깨질 수도 있어서 인코딩해서 보내주는 게 안전
    // 이 이름은 설치할 때의 파일 이름을 의미함
    String encodingUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

    // Content-Disposition 헤더를 넣어줘야 다운로드가 됨!!
    String contentDisposition = "attachment; filename=\"" + encodingUploadFileName + "\"";

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(urlResource);
  }

}
