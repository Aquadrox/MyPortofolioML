package sec.megaupload.controller;

import sec.megaupload.repository.FileRepository;
import sec.megaupload.domain.FileObject;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sec.megaupload.domain.Account;
import sec.megaupload.repository.AccountRepository;

@Controller
public class FileController {

    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String list(Authentication authentication, Model model) {
        model.addAttribute("files", accountRepository.findByUsername(authentication.getName()).getFileObjects());
        return "files";
    }

    //n'importe qui peut télécharger n'importe quoi.
    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id, Authentication authentication) {
        FileObject fo = fileRepository.findOne(id);
        Account FileAccount = fo.getAccount();

        if (FileAccount.getUsername().equals(authentication.getName())){
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fo.getContentType()));
            headers.add("Content-Disposition", "attachment; filename=" + fo.getName());
            headers.setContentLength(fo.getContentLength());

            return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
        }else{
            return null;
        }
        
    }

    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public String addFile(Authentication authentication, @RequestParam("file") MultipartFile file) throws IOException {
        Account account = accountRepository.findByUsername(authentication.getName());
        
        
        FileObject fileObject = new FileObject();
        fileObject.setContentType(file.getContentType());
        fileObject.setContent(file.getBytes());
        fileObject.setName(file.getOriginalFilename());
        fileObject.setContentLength(file.getSize());
        fileObject.setAccount(account);
        
        fileRepository.save(fileObject);
        

        return "redirect:/files";
    }

    //n'importe qui peut supprimer n'importe quoi
    @RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id, Authentication authentication) {
        
        FileObject fo = fileRepository.findOne(id);
        Account FileAccount = fo.getAccount();

        if (FileAccount.getUsername().equals(authentication.getName())){
            fileRepository.delete(id);
        }
        
        return "redirect:/files";
    }
}
