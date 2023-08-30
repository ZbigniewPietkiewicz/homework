package com.bit4mation.homework.repositories;

import com.bit4mation.homework.models.ClosureRecord;
import com.bit4mation.homework.models.Node;
import com.bit4mation.homework.models.Tree;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClosureRecordRepository extends JpaRepository<ClosureRecord, Long> {
    List<ClosureRecord> findAllByTree(Tree tree);

    List<ClosureRecord> findByParent(Node parent);

    List<ClosureRecord> findByChild(Node child);

    @Query(value = "SELECT id FROM closure_records WHERE child in (SELECT child FROM closure_records WHERE parent = ?1)", nativeQuery = true)
    List<Long> getSubtreeClosureRecordIds(long node_id);

    @Query(value = "SELECT child FROM closure_records WHERE tree = ?1 GROUP BY child HAVING COUNT(id) = 1", nativeQuery = true)
    List<Long> getRootNodeIdFromTreeId(long treeId);

}