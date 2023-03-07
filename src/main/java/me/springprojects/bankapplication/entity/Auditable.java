package me.springprojects.bankapplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @CreatedBy
    @Column(name = "created_by")
    protected String creator;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    protected String lastModifier;

    @CreatedDate
    @Column(name = "creation_date")
    protected LocalDate creationDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    protected LocalDate modifiedDate;
}
