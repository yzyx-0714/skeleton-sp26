public class RedBlackTree<T extends Comparable<T>> {

    /* 树的根节点。 */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * 创建一个红黑树节点，元素为 ITEM，颜色由 ISBLACK 的值决定。
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * 创建一个红黑树节点，元素为 ITEM，颜色由 ISBLACK 的值决定，
         * 左子节点为 LEFT，右子节点为 RIGHT。
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 创建一棵空的红黑树。
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * 翻转节点及其子节点的颜色。假设 NODE 同时拥有左子节点和右子节点。
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: 在此处编写你的代码

        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * 将给定节点向右旋转。返回该子树新的根节点。
     * 在此实现中，请确保交换新根节点和旧根节点的颜色！
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: 在此处编写你的代码
        RBTreeNode newNode = node.left;
        RBTreeNode sonRight = node.left.right;
        node.left.right = node;
        node.left = sonRight;
        newNode.isBlack = node.isBlack;
        node.isBlack = false;
        return newNode;
    }

    /**
     * 将给定节点向左旋转。返回该子树新的根节点。
     * 在此实现中，请确保交换新根节点和旧根节点的颜色！
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: 在此处编写你的代码
        RBTreeNode newNode = node.right;
        RBTreeNode sonLeft = node.right.left;
        node.right.left = node;
        node.right = sonLeft;
        newNode.isBlack = node.isBlack;
        node.isBlack = false;
        return newNode;
    }

    /**
     * 辅助方法，返回给定节点是否为红色。
     * 空节点（子节点或叶子节点）自动视为黑色。
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * 将元素插入红黑树。将树的根节点染为黑色。
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    /**dd
     * 将元素插入此红黑树的辅助方法。
     * 已提供注释以帮助分解问题。对于每种情况，请考虑执行相应操作所需的场景。
     * 请务必同时复习本类中的其他方法！
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        // TODO: 插入（返回）新的红色叶子节点。
         if (node == null) {
            //到底了，new对象添加
            return new RBTreeNode<>(false, item);
        }

        // TODO: 处理普通的二叉搜索树插入。
        int cmp = item.compareTo(node.item);
        if (cmp < 0) {
            //去左
            node.left = insertHelper(node.left, item);
        } else if (cmp > 0) {
            //去右
            node.right = insertHelper(node.right, item);
        }

        // TODO: 左旋操作
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }

        // TODO: 右旋操作
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        // TODO: 颜色翻转
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node; //修复此返回语句
    }

}