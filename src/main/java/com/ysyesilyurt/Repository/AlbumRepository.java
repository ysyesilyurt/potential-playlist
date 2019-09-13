package com.ysyesilyurt.Repository;

import com.ysyesilyurt.EntityModel.AlbumEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntityModel, Long> {
    // TODO: may add relationship queries to filter data
}
