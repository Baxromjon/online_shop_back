package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.EntityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.ATTACHMENT_CONTENT)
@SQLDelete(sql = "UPDATE " + EntityName.ATTACHMENT_CONTENT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class AttachmentContent extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Attachment attachment;

    @Column(name = "content")
    private byte[] content;
}
