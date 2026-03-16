<script setup>
import { Package, ArrowLeftRight, ClipboardList, Search, TrendingUp, Cuboid } from 'lucide-vue-next'

const menuItems = [
  { label: 'Produtos', path: '/', icon: Package },
  { label: 'Movimentação', path: '/movement', icon: ArrowLeftRight },
  { label: 'Histórico de movimentações', path: '/history', icon: ClipboardList },
  { label: 'Consulta por Tipo', path: '/products-by-type', icon: Search },
  { label: 'Lucro', path: '/profit', icon: TrendingUp },
]
</script>

<template>
  <aside class="w-64 bg-slate-900 text-slate-300 flex flex-col h-screen">
    <div
      class="h-16 flex items-center px-6 border-b border-slate-800 text-white font-bold text-xl gap-2"
    >
      <span class="text-blue-500">
        <Cuboid class="w-7 h-7" />
      </span>
      Estoque
    </div>

    <nav class="flex-1 px-4 py-6 space-y-1">
      <router-link
        v-for="item in menuItems"
        :key="item.path"
        :to="item.path"
        active-class="bg-slate-800 text-white font-medium"
        class="flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm hover:bg-slate-800 hover:text-white transition-colors"
      >
        <component :is="item.icon" class="w-5 h-5" />
        {{ item.label }}
      </router-link>
    </nav>
  </aside>
</template>
