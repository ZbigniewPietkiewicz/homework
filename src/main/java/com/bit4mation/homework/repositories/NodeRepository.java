package com.bit4mation.homework.repositories;

import com.bit4mation.homework.models.Node;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query(value = "SELECT nodes.* FROM nodes JOIN closure_records AS t on nodes.id = parent WHERE t.child = ?1 AND depth = 1", nativeQuery = true)
    Node getClosestParentNode(long node_id);

    @Query(value = "SELECT nodes.* FROM nodes JOIN closure_records AS t on nodes.id = child WHERE t.parent = ?1 AND is_leaf = true", nativeQuery = true)
    List<Node> getAllLeavesOnSubtree(long node_id);

    @Query(value = "SELECT nodes.* FROM nodes JOIN closure_records AS t on nodes.id = parent WHERE t.child = ?1 AND is_leaf = false", nativeQuery = true)
    List<Node> getAllParentNodes(long node_id);
}
