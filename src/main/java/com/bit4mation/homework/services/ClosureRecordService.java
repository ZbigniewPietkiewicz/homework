package com.bit4mation.homework.services;

import java.util.List;

import com.bit4mation.homework.models.ClosureRecord;
import com.bit4mation.homework.models.Node;
import com.bit4mation.homework.models.Tree;

public interface ClosureRecordService {
    List<ClosureRecord> getClosureRecordsOfTree(Tree tree);

    ClosureRecord getClosureRecordById(long id);

    void createSelfClosureRecord(Node node, Tree tree);

    void createClosureRecords(Node parentNode, Node childNode, Tree tree);

    void removeClosureRecords(long nodeToRemoveId);

    void updateClosureRecords(Node updatedNode);

    void updateClosureRecord(ClosureRecord closureRecord);

    void removeClosureRecordById(long id);

    Long getRootNodeIdFromTreeId(long treeId);
}