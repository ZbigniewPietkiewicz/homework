package com.bit4mation.homework.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit4mation.homework.models.ClosureRecord;
import com.bit4mation.homework.models.Node;
import com.bit4mation.homework.models.Tree;
import com.bit4mation.homework.repositories.ClosureRecordRepository;
import com.bit4mation.homework.services.ClosureRecordService;

@Service
public class ClosureRecordServiceImpl implements ClosureRecordService {

    @Autowired
    private ClosureRecordRepository closureRecordRepository;
    @Autowired
    private NodeServiceImpl nodeService;

    @Override
    public List<ClosureRecord> getClosureRecordsOfTree(Tree tree) {
        return closureRecordRepository.findAllByTree(tree);
    }

    @Override
    public ClosureRecord getClosureRecordById(long id) {
        Optional<ClosureRecord> closureRecordOptional = closureRecordRepository.findById(id);
        if (closureRecordOptional.isPresent()) {
            return closureRecordOptional.get();
        } else {
            throw new RuntimeException("Node not found");
        }
    }

    @Override
    public void createClosureRecords(Node parent, Node child, Tree tree) {
        createSelfClosureRecord(child, tree);

        // we have to check if this parent node has other children. if it has, it means
        // that our new node is side branch and we do not need to zero parent
        if (closureRecordRepository.findByParent(parent).size() == 1) {
            parent.setValue(0);
            nodeService.saveNode(child);
        }
        List<ClosureRecord> rc = closureRecordRepository.findByChild(parent);
        for (int i = 0; i < rc.size(); i++) {
            // self refernetial closure record (depth 0) is already created so all further
            // closure records will have higher depth.
            int depth = i + 1;
            Node parentNode = rc.get(i).getParent();
            parentNode.setLeaf(false);
            nodeService.saveNode(parentNode);

            ClosureRecord closureRecord = new ClosureRecord();
            closureRecord.setChild(child);
            closureRecord.setParent(rc.get(i).getParent());
            closureRecord.setDepth(depth);
            closureRecord.setTree(tree);
            updateClosureRecord(closureRecord);
        }
        updateClosureRecords(child);
    }

    @Override
    public void removeClosureRecords(long nodeToRemoveId) {
        Node parentNode = nodeService.getClosestParentNode(nodeToRemoveId);

        if (nodeService.getNodeById(nodeToRemoveId).isLeaf()) {
            removeClosureRecordById(nodeToRemoveId);
        } else {
            List<Long> closureRecordsToRemoveIds = closureRecordRepository
                    .getSubtreeClosureRecordIds(nodeToRemoveId);
            for (Long closureRecordToRemoveId : closureRecordsToRemoveIds) {
                removeClosureRecordById(closureRecordToRemoveId);
            }
        }

        // check if this node has multiple children. If it has, we can't make it a leaf.
        if (closureRecordRepository.findByParent(parentNode).size() == 1) {
            parentNode.setLeaf(true);
            nodeService.saveNode(parentNode);
            updateClosureRecords(parentNode);
        }
    }

    @Override
    public void createSelfClosureRecord(Node node, Tree tree) {
        ClosureRecord closureRecord = new ClosureRecord();
        closureRecord.setParent(node);
        closureRecord.setChild(node);
        closureRecord.setTree(tree);
        updateClosureRecord(closureRecord);
    }

    @Override
    public void updateClosureRecords(Node updatedNode) {
        List<Node> affectedLeaves = nodeService.getAllLeavesOnSubtree(updatedNode.getId());
        for (Node affectedLeaf : affectedLeaves) {
            int sum = 0;
            List<Node> affectedPath = nodeService.getAllParentNodes(affectedLeaf.getId());
            for (Node nodeOfPath : affectedPath) {
                sum = sum + nodeOfPath.getValue();
            }
            affectedLeaf.setValue(sum);
            nodeService.saveNode(affectedLeaf);
        }
    }

    @Override
    public void updateClosureRecord(ClosureRecord closureRecord) {
        closureRecordRepository.save(closureRecord);
    }

    @Override
    public void removeClosureRecordById(long id) {
        nodeService.removeNodeById(getClosureRecordById(id).getChild().getId());
        closureRecordRepository.deleteById(id);
    }

    @Override
    public Long getRootNodeIdFromTreeId(long treeId) {
        return closureRecordRepository.getRootNodeIdFromTreeId(treeId).get(0);
    }

}
