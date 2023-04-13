package com.musala.musalatest.business.dao;

import com.musala.musalatest.business.model.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {

    Optional<Dispatch> findDispatchByReference(String reference);
}
