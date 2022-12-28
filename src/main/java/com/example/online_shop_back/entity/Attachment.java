package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.EntityName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.ATTACHMENT)
@SQLDelete(sql = "UPDATE " + EntityName.ATTACHMENT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Attachment extends AbsEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private long size;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "attachment",cascade = CascadeType.ALL)
    private AttachmentContent attachmentContent;

    public Attachment(String name, long size, String contentType) {
        this.name = name;
        this.size = size;
        this.contentType = contentType;
    }
}
