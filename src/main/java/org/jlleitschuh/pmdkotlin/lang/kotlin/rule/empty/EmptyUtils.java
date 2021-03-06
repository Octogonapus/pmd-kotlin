package org.jlleitschuh.pmdkotlin.lang.kotlin.rule.empty;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.impl.source.tree.PsiCoreCommentImpl;
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import org.jetbrains.kotlin.psi.KtExpression;

public class EmptyUtils {
    static boolean isEmptyBlock(KtExpression body) {
        for (PsiElement child = body.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof LeafPsiElement &&
                    (child.getText().equals("{") || child.getText().equals("}"))) {
                continue;
            }
            if (!(child instanceof PsiWhiteSpaceImpl)
                    && !(child instanceof PsiCoreCommentImpl)) {
                return false;
            }
        }

        return true;
    }
}
