package com.ysyesilyurt.Repository;

import com.ysyesilyurt.EntityModel.SongEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<SongEntityModel, Long> {
    // TODO: may add relationship queries to filter data
}
