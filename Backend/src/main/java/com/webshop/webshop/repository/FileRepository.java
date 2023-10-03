package com.webshop.webshop.repository;

import com.webshop.webshop.model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {

    File findByFileName(String fileName);

}
