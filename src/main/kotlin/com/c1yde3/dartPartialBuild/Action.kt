package com.c1yde3.dartPartialBuild

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.isFile
import org.jetbrains.plugins.terminal.TerminalToolWindowManager

class BuildAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return


        val dir = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE) ?: return


        val terminal = TerminalToolWindowManager.getInstance(project).createLocalShellWidget(project.basePath, "build")

        if (dir.isDirectory) {
            terminal.executeCommand("dart run build_runner build -d --build-filter=\"${dir.path}/**.dart\"")
        }

        if (dir.isFile) {
            terminal.executeCommand("dart run build_runner build -d --build-filter=\"${dir.parent.path}/**.dart\"")
        }
    }

}

class WatchAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return


        val dir = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE) ?: return


        val terminal = TerminalToolWindowManager.getInstance(project).createLocalShellWidget(project.basePath, "build")

        if (dir.isDirectory) {
            terminal.executeCommand("dart run build_runner watch -d --build-filter=\"${dir.path}/**.dart\"")
        }

        if (dir.isFile) {
            terminal.executeCommand("dart run build_runner watch -d --build-filter=\"${dir.parent.path}/**.dart\"")
        }
    }

}