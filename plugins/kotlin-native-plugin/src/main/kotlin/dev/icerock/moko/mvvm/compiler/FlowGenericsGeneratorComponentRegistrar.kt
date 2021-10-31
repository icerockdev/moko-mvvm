/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.compiler

import com.google.auto.service.AutoService
import com.intellij.mock.MockProject
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.ir.builders.declarations.addGetter
import org.jetbrains.kotlin.ir.builders.declarations.addProperty
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrProperty
import org.jetbrains.kotlin.ir.types.IrSimpleType
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.util.parentClassOrNull
import org.jetbrains.kotlin.name.Name

@AutoService(ComponentRegistrar::class)
open class FlowGenericsGeneratorComponentRegistrar : ComponentRegistrar {

    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        val messageCollector: MessageCollector =
            configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

        IrGenerationExtension.registerExtension(
            project,
            GenerateSourcesExtension(messageCollector)
        )
    }
}

typealias GenerationAction = () -> Unit

class GenerateSourcesExtension(
    private val messageCollector: MessageCollector
) : IrGenerationExtension {
    private fun debug(message: String) {
        messageCollector.report(CompilerMessageSeverity.WARNING, message)
    }

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        debug("module $moduleFragment context $pluginContext")
        val actions = moduleFragment.files.flatMap { generateForFile(it, pluginContext) }
        actions.forEach { it.invoke() }
    }

    private fun generateForFile(
        file: IrFile,
        pluginContext: IrPluginContext
    ): List<GenerationAction> {
        debug("file $file context $pluginContext")
        return file.declarations.flatMap { generateForDeclaration(it, pluginContext) }
    }

    private fun generateForDeclaration(
        declaration: IrDeclaration,
        pluginContext: IrPluginContext
    ): List<GenerationAction> {
        debug("declaration $declaration context $pluginContext")
        return when (declaration) {
            is IrProperty -> generateForProperty(declaration, pluginContext)
            is IrClass -> generateForClass(declaration, pluginContext)
            else -> emptyList()
        }
    }

    private fun generateForClass(
        clazz: IrClass,
        pluginContext: IrPluginContext
    ): List<GenerationAction> {
        debug("class $clazz context $pluginContext")
        return clazz.declarations.flatMap { generateForDeclaration(it, pluginContext) }
    }

    private fun generateForProperty(
        property: IrProperty,
        pluginContext: IrPluginContext
    ): List<GenerationAction> {
        debug("property $property context $pluginContext")
        val getter = property.getter
        if (getter == null) {
            debug("skip $property - here no getter")
            return emptyList()
        }
        if (getter.visibility.isPublicAPI.not()) {
            debug("skip $getter - it's not public API")
            return emptyList()
        }

        val returnType = getter.returnType
        if (returnType !is IrSimpleType) {
            debug("skip $getter - return type $returnType is not IrSimpleType")
            return emptyList()
        }

        val className = returnType.classFqName
        if (className == null) {
            debug("skip $getter - return type $returnType class is null")
            return emptyList()
        }

        val typeParameters = returnType.arguments

        val parentClass = property.parentClassOrNull
        if (parentClass != null) {
            return listOf {
                val newName = Name.identifier(property.name.identifier + "Gen")
                parentClass.addProperty {
                    this.name = newName
                    this.visibility = DescriptorVisibilities.PUBLIC
                }.addGetter {
                    this.returnType = getter.returnType
                }.apply {
                    this.body = DeclarationIrBuilder(pluginContext, symbol).irBlockBody {
                        +irCall(getter)
                    }
                }
                debug("property $newName for ${parentClass.name} added")
            }
        }

        return emptyList()
    }
}
