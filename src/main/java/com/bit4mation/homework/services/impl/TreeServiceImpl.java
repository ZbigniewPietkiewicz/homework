package com.bit4mation.homework.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit4mation.homework.models.Tree;
import com.bit4mation.homework.models.Node;
import com.bit4mation.homework.repositories.TreeRepository;
import com.bit4mation.homework.services.TreeService;

@Service
public class TreeServiceImpl implements TreeService {

    @Autowired
    private TreeRepository treeRepository;
    @Autowired
    private NodeServiceImpl nodeService;
    @Autowired
    private ClosureRecordServiceImpl closureRecordService;

    @Override
    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    @Override
    public Tree getTreeById(long id) {
        Optional<Tree> potentialTree = treeRepository.findById(id);
        if (potentialTree.isPresent()) {
            return potentialTree.get();
        } else {
            throw new RuntimeException("Tree not found");
        }
    }

    @Override
    public void createTree(Tree tree) {
        treeRepository.save(tree);

        Node rootNode = nodeService.createNode();

        closureRecordService.createSelfClosureRecord(rootNode, tree);
    }

    @Override
    public void addNodeToTree(Tree tree, long parentNodeId) {
        Node parentNode = nodeService.getNodeById(parentNodeId);
        Node newNode = nodeService.createNode();
        closureRecordService.createClosureRecords(parentNode, newNode, tree);
    }

    @Override
    public void removeNodeFromTree(long parentNodeId) {
        closureRecordService.removeClosureRecords(parentNodeId);
    }

    @Override
    public void removeTreeById(long id) {
        treeRepository.deleteById(id);
    }
}
