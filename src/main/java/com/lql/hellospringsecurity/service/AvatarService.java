package com.lql.hellospringsecurity.service;


import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.exception.model.MyUsernameNotFoundException;
import com.lql.hellospringsecurity.model.AvatarImage;
import com.lql.hellospringsecurity.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository repository;


    public String uploadFile(MultipartFile file, CustomUser user) throws IOException {

        AvatarImage image = new AvatarImage(ImageUtils.compressImage(file.getBytes()), user);
        repository.save(image);

        return "File saved successfully " + file.getOriginalFilename();
    }
    public byte[] downloadImageByUserId(long userId) {

        Optional<AvatarImage> avatarImage = repository.findById(userId);
        if(avatarImage.isPresent())
            return ImageUtils.decompressImage(avatarImage.get().getImage());

        throw new MyUsernameNotFoundException();
    }


}
