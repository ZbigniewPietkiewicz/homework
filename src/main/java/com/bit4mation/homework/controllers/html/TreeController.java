package com.bit4mation.homework.controllers.html;

import com.bit4mation.homework.models.ClosureRecord;
import com.bit4mation.homework.models.Node;
import com.bit4mation.homework.models.Tree;
import com.bit4mation.homework.services.ClosureRecordService;
import com.bit4mation.homework.services.NodeService;
import com.bit4mation.homework.services.TreeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TreeController {

    @Autowired
    private TreeService treeService;
    @Autowired
    private ClosureRecordService closureRecordService;
    @Autowired
    private NodeService nodeService;

    @GetMapping("tree/add")
    public String showNewTreeForm(Model model) {
        Tree tree = new Tree();
        model.addAttribute("tree", tree);
        return "tree/new_tree";
    }

    @PostMapping("tree/save")
    public String saveTree(@ModelAttribute("tree") Tree tree) {
        treeService.createTree(tree);
        return "redirect:/";
    }

    @GetMapping("tree/open/{id}")
    public String openTree(
            @PathVariable(value = "id") long id,
            Model model) {
        Tree tree = treeService.getTreeById(id);

        model.addAttribute("tree", tree);

        List<ClosureRecord> closureRecords = closureRecordService.getClosureRecordsOfTree(tree);

        model.addAttribute("closureRecords", closureRecords);

        return "tree/tree_view";
    }

    @GetMapping("/tree/addNode/{tree_id}/{parent_node_id}")
    public String addNodeToTree(
            @PathVariable(value = "tree_id") long treeId,
            @PathVariable(value = "parent_node_id") long parentNodeId,
            Model model) {
        Tree tree = treeService.getTreeById(treeId);
        treeService.addNodeToTree(tree, parentNodeId);
        return "redirect:/tree/open/" + treeId;
    }

    @GetMapping("/tree/removeNode/{tree_id}/{parent_node_id}")
    public String removeNodeFromTree(
            @PathVariable(value = "tree_id") long treeId,
            @PathVariable(value = "parent_node_id") long parentNodeId,
            Model model) {
        treeService.removeNodeFromTree(parentNodeId);
        return "redirect:/tree/open/" + treeId;
    }

    @GetMapping("/tree/updateNode/{tree_id}/{modified_node_id}")
    public String updateNodeOfTree(
            @PathVariable(value = "tree_id") long treeId,
            @PathVariable(value = "modified_node_id") long modifiedNodeId,
            Model model) {
        model.addAttribute("node", nodeService.getNodeById(modifiedNodeId));
        model.addAttribute("tree_id", treeId);
        return "tree/update_node";
    }

    @PostMapping("/tree/saveNode/{tree_id}")
    public String saveNode(
            @PathVariable(value = "tree_id") long treeId,
            @ModelAttribute("node") Node node) {
        nodeService.saveNode(node);
        closureRecordService.updateClosureRecords(node);
        return "redirect:/tree/open/" + treeId;
    }

    @GetMapping("tree/delete/{id}")
    public String deleteTree(@PathVariable(value = "id") long id) {
        treeService.removeTreeById(id);

        return "redirect:/";
    }
}