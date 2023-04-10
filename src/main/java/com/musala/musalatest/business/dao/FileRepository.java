package com.musala.musalatest.business.dao;

import com.musala.musalatest.business.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileStorage, String> {
}