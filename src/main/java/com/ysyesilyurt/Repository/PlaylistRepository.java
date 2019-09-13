package com.ysyesilyurt.Repository;

import com.ysyesilyurt.EntityModel.PlaylistEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntityModel, Long> {
    // TODO: may add relationship queries to filter data
}
