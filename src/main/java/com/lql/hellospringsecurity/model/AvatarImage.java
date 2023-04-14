package com.lql.hellospringsecurity.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Timestamp createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public AvatarImage(long id, byte[] image) {
        this.id = id;
        this.image = image;
    }
}
