package com.bit4mation.homework.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit4mation.homework.models.Node;
import com.bit4mation.homework.repositories.NodeRepository;
import com.bit4mation.homework.services.NodeService;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepository nodeRepository;

    @Override
    public Node getNodeById(long id) {
        Optional<Node> nodeOptional = nodeRepository.findById(id);
        if (nodeOptional.isPresent()) {
            return nodeOptional.get();
        } else {
            throw new RuntimeException("Node not found");
        }
    }

    @Override
    public Node getClosestParentNode(long id) {
        return nodeRepository.getClosestParentNode(id);
    }

    @Override
    public List<Node> getAllLeavesOnSubtree(long id) {
        return nodeRepository.getAllLeavesOnSubtree(id);
    }

    @Override
    public List<Node> getAllParentNodes(long id) {
        return nodeRepository.getAllParentNodes(id);
    }

    @Override
    public Node createNode() {
        Node node = new Node();
        nodeRepository.save(node);
        return node;
    }

    @Override
    public void saveNode(Node node) {
        nodeRepository.save(node);
    }

    @Override
    public void removeNodeById(long id) {
        nodeRepository.deleteById(id);
    }
}
