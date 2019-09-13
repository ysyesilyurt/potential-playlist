package com.ysyesilyurt.Repository;

import com.ysyesilyurt.EntityModel.ArtistEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntityModel, Long> {
    // TODO: may add relationship queries to filter data
}
