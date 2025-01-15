package com.example.board_project.global.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity implements Persistable<String> {
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deletedAt;

    public void delete(LocalDateTime currentTime) {
        if (deletedAt == null) {
            deletedAt = currentTime;
        }
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
