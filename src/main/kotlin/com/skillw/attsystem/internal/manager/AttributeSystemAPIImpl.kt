package com.skillw.attsystem.internal.manager

import com.skillw.attsystem.AttributeSystem
import com.skillw.attsystem.api.AttributeSystemAPI
import com.skillw.pouvoir.util.isAlive
import com.skillw.pouvoir.util.livingEntity
import org.bukkit.entity.LivingEntity
import java.util.*

object AttributeSystemAPIImpl : AttributeSystemAPI {

    override val key = "AttributeSystemAPI"
    override val priority: Int = 100
    override val subPouvoir = AttributeSystem

    override fun onActive() {
        onReload()
    }

    override fun update(entity: LivingEntity) {
        if (!entity.isAlive()) return
        AttributeSystem.equipmentDataManager.update(entity)
        //��һ�θ���������������
        AttributeSystem.attributeDataManager.update(entity)
        //��һ�θ��������������ԣ���Щ����������������Ϊ������
        AttributeSystem.attributeDataManager.update(entity)
        AttributeSystem.realizerManager.realize(entity)
    }


    override fun remove(entity: LivingEntity) {
        this.remove(entity.uniqueId)
    }


    override fun remove(uuid: UUID) {
        AttributeSystem.attributeDataManager.remove(uuid)
        AttributeSystem.equipmentDataManager.remove(uuid)
        uuid.livingEntity()?.let { AttributeSystem.realizerManager.unrealize(it) }
    }

}
