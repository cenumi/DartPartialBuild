package com.c1yde3.dartPartialBuild

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.isFile
import org.jetbrains.plugins.terminal.TerminalToolWindowManager


private fun createAndExecute(module: Module, selectedFile: VirtualFile, command: String) {


    val modulePath = ModuleRootManager.getInstance(module).contentRoots.first().path

    val terminal =
        TerminalToolWindowManager.getInstance(module.project).createLocalShellWidget(modulePath, command)


    if (selectedFile.isDirectory) {
        terminal.executeCommand("dart run build_runner $command -d --build-filter=\"${selectedFile.path}/*/**.dart\"")
    }

    if (selectedFile.isFile) {
        terminal.executeCommand("dart run build_runner $command -d --build-filter=\"${selectedFile.parent.path}/*/**.dart\"")
    }
}


class BuildAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val module = e.getData(LangDataKeys.MODULE) ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        createAndExecute(module, file, "build")
    }

}

class WatchAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val module = e.getData(LangDataKeys.MODULE) ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        createAndExecute(module, file, "watch")
    }

}