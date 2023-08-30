package com.bit4mation.homework.services;

import java.util.List;

import com.bit4mation.homework.models.Tree;

public interface TreeService {
    List<Tree> getAllTrees();

    Tree getTreeById(long id);

    void createTree(Tree tree);

    void addNodeToTree(Tree tree, long parentNodeId);

    void removeNodeFromTree(long parentNodeId);

    void removeTreeById(long id);
}