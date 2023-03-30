package com.lql.hellospringsecurity.model;


import com.lql.hellospringsecurity.auth.CustomUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "avatar")
public class AvatarImage {

    @Id
    @Column(name = "user_id")
    private long id;


    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @CreatedDate
    private Timestamp createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private CustomUser user;


    public AvatarImage(byte[] image, CustomUser user) {
        this.image = image;
        this.user = user;
    }
}
