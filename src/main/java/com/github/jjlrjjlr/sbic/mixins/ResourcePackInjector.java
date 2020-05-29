package com.github.jjlrjjlr.sbic.mixins;

import com.github.jjlrjjlr.sbic.LoaderUtilities.SBICResources;
import com.mojang.datafixers.DataFixer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackProfile;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.datafixer.Schemas;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class ResourcePackInjector {
    @Final @Shadow private ResourcePackManager<ClientResourcePackProfile> resourcePackManager;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/datafixer/Schemas;getFixer()Lcom/mojang/datafixers/DataFixer;"))
    private DataFixer resourcePackRegister(){
        this.resourcePackManager.registerProvider(new SBICResources());
        return Schemas.getFixer();
    }
}