package com.example.widget.repository;

import com.example.widget.domain.WidgetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<WidgetEntity, Long>, PagingAndSortingRepository<WidgetEntity, Long> {
    Page<WidgetEntity> findAll(Pageable pageRequest);
}
