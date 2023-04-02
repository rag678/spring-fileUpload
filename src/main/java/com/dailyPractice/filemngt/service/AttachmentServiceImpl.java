package com.dailyPractice.filemngt.service;

import com.dailyPractice.filemngt.entity.Attachment;
import com.dailyPractice.filemngt.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentServiceImpl implements AttachmentService{

    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")){
                throw new Exception("Filename contain invalid path sequence" + fileName);
            }

            Attachment attachment = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());

            return attachmentRepository.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could Not save file "+fileName);
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId)
                .orElseThrow(()->new Exception("File Not Found with id : " + fileId));
    }
}
