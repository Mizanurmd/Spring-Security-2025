package com.cns.security.utils;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


import com.cns.security.entity.OurUser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@MappedSuperclass
@Getter
@Setter
@SuperBuilder
public class CommonEntity {
	  @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private UUID id;

	  
	    @Temporal(TemporalType.TIMESTAMP)
	    private LocalDateTime createdAt;

	    @ManyToOne(optional = false)
	    @JoinColumn(name = "created_by", nullable = false, updatable = false)
	    private OurUser createdBy;

	  
	    @Temporal(TemporalType.TIMESTAMP)
	    private LocalDateTime updatedAt;

	    @ManyToOne(optional = false)
	    @JoinColumn(name = "updated_by", nullable = false)
	    private OurUser updatedBy;

	
	    @Temporal(TemporalType.TIMESTAMP)
	    private LocalDateTime deletedAt;

	    @ManyToOne
	    @JoinColumn(name = "deleted_by")
	    private OurUser deletedBy;

	    @PrePersist
	    protected void onCreate() {
	        this.createdAt = LocalDateTime.now();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        this.updatedAt = LocalDateTime.now();
	    }

	   
	
	

}
