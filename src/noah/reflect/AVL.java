package noah.reflect;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: HuWan Peng
 * @Date Created in 10:35 2017/12/29
 */
public class AVL {
    Node root; // 根结点

    private class Node {
        int key, val;
        Node left, right;
        int height = 1; // 每个结点的高度属性

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * @description: 返回两个数中的最大值
     */
    private int max(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * @description: 获得当前结点的高度
     */
    private int height(Node x) {
        if (x == null) return 0;
        return x.height;
    }

    /**
     * @description: 获得平衡因子
     */
    private int getBalance(Node x) {
        if (x == null) return 0;
        return height(x.left) - height(x.right);
    }

    /**
     * @description: 右旋方法
     */
    private Node rotateRight(Node x) {
        Node y = x.left; // 取得x的左儿子
        x.left = y.right; // 将x左儿子的右儿子（"拖油瓶"结点）链接到旋转后的x的左链接中
        y.right = x; // 调转x和它左儿子的父子关系，使x成为它原左儿子的右子树
        x.height = max(height(x.left), height(x.right)) + 1; // 更新并维护受影响结点
        y.height = max(height(y.left), height(y.right)) + 1; // 更新并维护受影响结点
        return y; // 将y返回
    }

    /**
     * @description: 左旋方法
     */
    private Node rotateLeft(Node x) {
        Node y = x.right;  // 取得x的右儿子
        x.right = y.left; // 将x右儿子的左儿子（"拖油瓶"结点）链接到旋转后的x的右链接中
        y.left = x; // 调转x和它右儿子的父子关系，使x成为它原右儿子的左子树
        x.height = max(height(x.left), height(x.right)) + 1; // 更新并维护受影响结点
        y.height = max(height(y.left), height(y.right)) + 1; // 更新并维护受影响结点
        return y; // 将y返回
    }

    /**
     * @description: 平衡化操作
     */
    private Node reBalance(Node x) {
        int balanceFactor = getBalance(x);
        if (balanceFactor > 1 && getBalance(x.left) > 0) { // LL型，进行单次右旋
            return rotateRight(x);
        }
        if (balanceFactor > 1 && getBalance(x.left) <= 0) { //LR型 先左旋再右旋
            Node t = rotateLeft(x);
            return rotateRight(t);
        }
        if (balanceFactor < -1 && getBalance(x.right) <= 0) {//RR型， 进行单次左旋
            return rotateLeft(x);
        }
        if (balanceFactor < -1 && getBalance(x.right) > 0) {// RL型，先右旋再左旋
            Node t = rotateRight(x);
            return rotateLeft(t);
        }
        return x;
    }

    /**
     * @description: 插入结点（键值对）
     */
    public Node put(Node x, int key, int val) {
        if (x == null) return new Node(key, val); // 插入键值对
        if (key < x.key) x.left = put(x.left, key, val); // 向左子树递归插入
        else if (key > x.key) x.right = put(x.right, key, val); // 向右子树递归插入
        else x.val = val; // key已存在， 替换val
        x.height = max(height(x.left), height(x.right)) + 1; // 沿递归路径从下至上更新结点height属性
        x = reBalance(x); // 沿递归路径从下往上, 检测当前结点是否失衡，若失衡则进行平衡化
        return x;
    }

    public void put(int key, int val) {
        root = put(root, key, val);
    }

    /**
     * @description: 返回最小键
     */
    private Node min(Node x) {
        if (x.left == null) return x; // 如果左儿子为空，则当前结点键为最小值，返回
        return min(x.left);  // 如果左儿子不为空，则继续向左递归
    }

    public int min() {
        if (root == null) return -1;
        return min(root).key;
    }

    /**
     * @description: 删除最小键的结点
     */
    public Node deleteMin(Node x) {
        if (x.left == null) return x.right; // 如果当前结点左儿子空，则将右儿子返回给上一层递归的x.left
        x.left = deleteMin(x.left);// 向左子树递归， 同时重置搜索路径上每个父结点指向左儿子的链接
        return x; // 当前结点不是min
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    /**
     * @description: 删除给定key的键值对
     */
    private Node delete(int key, Node x) {
        if (x == null) return null;
        if (key < x.key) x.left = delete(key, x.left); // 向左子树查找键为key的结点
        else if (key > x.key) x.right = delete(key, x.right); // 向右子树查找键为key的结点
        else {
            // 结点已经被找到，就是当前的x
            if (x.left == null) return x.right; // 如果左子树为空，则将右子树赋给父节点的链接
            if (x.right == null) return x.left; // 如果右子树为空，则将左子树赋给父节点的链接
            Node inherit = min(x.right); // 取得结点x的继承结点
            inherit.right = deleteMin(x.right); // 将继承结点从原来位置删除，并重置继承结点右链接
            inherit.left = x.left; // 重置继承结点左链接
            x = inherit; // 将x替换为继承结点
        }
        if (root == null) return root;
        x.height = max(height(x.left), height(x.right)) + 1; // 沿递归路径从下至上更新结点height属性
        x = reBalance(x); // 沿递归路径从下往上, 检测当前结点是否失衡，若失衡则进行平衡化
        return x;
    }

    public void delete(int key) {
        root = delete(key, root);
    }

    private void levelIterator() {
        LinkedList<Node> queue = new LinkedList<>();
        Node current = null;
        int childSize = 0;
        int parentSize = 1;
        queue.offer(root);
        while (!queue.isEmpty()) {
            current = queue.poll();//出队队头元素并访问
            System.out.print(current.val + "-->");
            if (current.left != null)//如果当前节点的左节点不为空入队
            {
                queue.offer(current.left);
                childSize++;
            }
            if (current.right != null)//如果当前节点的右节点不为空，把右节点入队
            {
                queue.offer(current.right);
                childSize++;
            }
            parentSize--;
            if (parentSize == 0) {
                parentSize = childSize;
                childSize = 0;
                System.out.println("");
            }
        }
        qianxu(root, new LinkedList<>());
        System.out.println();
        zhongxu(root, new LinkedList<>());
        System.out.println();
        depthOrderTraverse(root);

    }

    public void qianxu(Node root, LinkedList<Node> queue) {
        Stack<Node> stack = new Stack<>();
        Node pNode = root;
        while (pNode != null || !stack.isEmpty()) {
            if (pNode != null) {
                System.out.print(pNode.val + "-->");
                stack.push(pNode);
                pNode = pNode.left;
            } else { //pNode == null && !stack.isEmpty()
                Node node = stack.pop();
                pNode = node.right;
            }
        }
    }

    public void zhongxu(Node root, LinkedList<Node> queue) {
        Stack<Node> stack = new Stack<>();
        Node pNode = root;
        while (pNode != null || !stack.isEmpty()) {
            if (pNode != null) {
                stack.push(pNode);
                pNode = pNode.left;
            } else { //pNode == null && !stack.isEmpty()
                Node node = stack.pop();
                System.out.print(node.val + "-->");
                pNode = node.right;
            }
        }
    }

    public void depthOrderTraverse(Node root) {
        if (root == null) {
            return;
        }
        LinkedList<Node> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.val + "  ");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public static void main(String... args) {
        AVL avl = new AVL();
        avl.put(1, 11);
        avl.put(2, 22);
        avl.put(3, 33);
        avl.put(4, 44);
        avl.put(5, 55);
        avl.put(6, 66);
        avl.levelIterator();
        List<Integer> integerStream =
                IntStream.rangeClosed(1, 10).boxed().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        List<String> list = Collections.synchronizedList(new ArrayList<>());
    }
}