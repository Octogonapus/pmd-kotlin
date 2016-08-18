package org.jetbrains.pmdkotlin.lang.kotlin

import com.intellij.lang.ASTNode
import com.intellij.lang.impl.PsiBuilderImpl
import com.intellij.psi.impl.source.tree.FileElement
import net.sourceforge.pmd.lang.Parser
import net.sourceforge.pmd.lang.ParserOptions
import net.sourceforge.pmd.lang.TokenManager
import net.sourceforge.pmd.lang.ast.ParseException
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.pmdkotlin.lang.kotlin.ast.AbstractKotlinNode
import org.jetbrains.pmdkotlin.lang.kotlin.ast.KotlinASTNodeAdapter
import java.io.Reader
import java.util.*

class KotlinParser(private val parserOptions: ParserOptions) : Parser {
    private var parser: org.jetbrains.kotlin.parsing.KotlinParser? = null

    init {
        val intendedParser = KotlinFileContext.parserDefinition.createParser(KotlinFileContext.project)
        if (intendedParser is org.jetbrains.kotlin.parsing.KotlinParser) {
            parser = intendedParser
        } else {
            throw IllegalStateException("KotlinParser not found!")
        }
    }


    override fun getParserOptions(): ParserOptions {
        return this.parserOptions
    }

    override fun getTokenManager(s: String, reader: Reader): TokenManager {
        return KotlinTokenManager(KotlinFileContext(s, reader))
    }

    override fun canParse(): Boolean {
        return true
    }

    private fun showTree(v: ASTNode, s: String) {
        //System.err.println(s + v.getClass());
        if (v !is FileElement) {
            val psi = v.psi

            System.err.println(s + v.psi.javaClass)
        }
        for (cv in v.getChildren(null)) {
            showTree(cv, s + "  ")
        }
    }

    @Throws(ParseException::class)
    override fun parse(fileName: String, source: Reader): AbstractKotlinNode {
        val kotlinContext = KotlinFileContext(fileName, source)
        try {
            val builder = PsiBuilderImpl(KotlinFileContext.project, kotlinContext.psiFile, KotlinFileContext.parserDefinition, KotlinTokenManager(kotlinContext), null, kotlinContext.getSourceCode(), null, null)
            val root = parser?.parse(KtNodeTypes.KT_FILE, builder, kotlinContext.psiFile) as FileElement
            root.psi = kotlinContext.psiFile.node.psi

            //            showTree(root, "Parsed tree");

            return KotlinASTNodeAdapter(root.psi, kotlinContext)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    override fun getSuppressMap(): Map<Int, String> {
        return HashMap()
    }
}