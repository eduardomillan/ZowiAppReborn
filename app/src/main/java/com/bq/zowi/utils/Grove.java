package com.bq.zowi.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NonNls;

/* JADX INFO: loaded from: classes.dex */
public final class Grove {
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    private static final List<Tree> FOREST = new ArrayList();
    private static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;
    private static final Tree TREE_OF_SOULS = new Tree() { // from class: com.bq.zowi.utils.Grove.1
        @Override // com.bq.zowi.utils.Grove.Tree
        public void v(String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.v(message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void v(Throwable t, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.v(t, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void d(String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.d(message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void d(Throwable t, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.d(t, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void i(String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.i(message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void i(Throwable t, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.i(t, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void w(String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.w(message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void w(Throwable t, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.w(t, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void e(String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.e(message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void e(Throwable t, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.e(t, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void wtf(String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.wtf(message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void wtf(Throwable t, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.wtf(t, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void log(int priority, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.log(priority, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void log(int priority, Throwable t, String message, Object... args) {
            Tree[] forest = Grove.forestAsArray;
            for (Tree tree : forest) {
                tree.log(priority, t, message, args);
            }
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void log(int priority, String tag, String message, Throwable t) {
            throw new AssertionError("Missing override for log method.");
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public ThreadLocal<String> explicitTag() {
            return null;
        }
    };

    public interface Tree {
        void d(String str, Object... objArr);

        void d(Throwable th, String str, Object... objArr);

        void e(String str, Object... objArr);

        void e(Throwable th, String str, Object... objArr);

        ThreadLocal<String> explicitTag();

        void i(String str, Object... objArr);

        void i(Throwable th, String str, Object... objArr);

        void log(int i, String str, String str2, Throwable th);

        void log(int i, String str, Object... objArr);

        void log(int i, Throwable th, String str, Object... objArr);

        void v(String str, Object... objArr);

        void v(Throwable th, String str, Object... objArr);

        void w(String str, Object... objArr);

        void w(Throwable th, String str, Object... objArr);

        void wtf(String str, Object... objArr);

        void wtf(Throwable th, String str, Object... objArr);
    }

    public static void v(@NonNls String message, Object... args) {
        TREE_OF_SOULS.v(message, args);
    }

    public static void v(Throwable t, @NonNls String message, Object... args) {
        TREE_OF_SOULS.v(t, message, args);
    }

    public static void d(@NonNls String message, Object... args) {
        TREE_OF_SOULS.d(message, args);
    }

    public static void d(Throwable t, @NonNls String message, Object... args) {
        TREE_OF_SOULS.d(t, message, args);
    }

    public static void i(@NonNls String message, Object... args) {
        TREE_OF_SOULS.i(message, args);
    }

    public static void i(Throwable t, @NonNls String message, Object... args) {
        TREE_OF_SOULS.i(t, message, args);
    }

    public static void w(@NonNls String message, Object... args) {
        TREE_OF_SOULS.w(message, args);
    }

    public static void w(Throwable t, @NonNls String message, Object... args) {
        TREE_OF_SOULS.w(t, message, args);
    }

    public static void e(@NonNls String message, Object... args) {
        TREE_OF_SOULS.e(message, args);
    }

    public static void e(Throwable t, @NonNls String message, Object... args) {
        TREE_OF_SOULS.e(t, message, args);
    }

    public static void wtf(@NonNls String message, Object... args) {
        TREE_OF_SOULS.wtf(message, args);
    }

    public static void wtf(Throwable t, @NonNls String message, Object... args) {
        TREE_OF_SOULS.wtf(t, message, args);
    }

    public static void log(int priority, @NonNls String message, Object... args) {
        TREE_OF_SOULS.log(priority, message, args);
    }

    public static void log(int priority, Throwable t, @NonNls String message, Object... args) {
        TREE_OF_SOULS.log(priority, t, message, args);
    }

    public static Tree asTree() {
        return TREE_OF_SOULS;
    }

    public static Tree tag(String tag) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.explicitTag().set(tag);
        }
        return TREE_OF_SOULS;
    }

    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == TREE_OF_SOULS) {
            throw new IllegalArgumentException("Cannot plant Grove into itself.");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    public static void uproot(Tree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                throw new IllegalArgumentException("Cannot uproot tree which is not planted: " + tree);
            }
            forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    public static void uprootAll() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    public static List<Tree> forest() {
        List<Tree> listUnmodifiableList;
        synchronized (FOREST) {
            listUnmodifiableList = Collections.unmodifiableList(new ArrayList(FOREST));
        }
        return listUnmodifiableList;
    }

    private Grove() {
        throw new AssertionError("No instances.");
    }
}
