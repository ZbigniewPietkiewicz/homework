package com.bit4mation.homework.services;

import java.util.List;

import com.bit4mation.homework.models.Node;

public interface NodeService {
    Node getNodeById(long id);

    Node getClosestParentNode(long id);

    List<Node> getAllLeavesOnSubtree(long id);

    List<Node> getAllParentNodes(long id);

    Node createNode();

    void saveNode(Node node);

    void removeNodeById(long id);
}