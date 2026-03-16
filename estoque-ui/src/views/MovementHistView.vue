<script setup>
import { ref, onMounted } from 'vue'
import { ArrowDownToLine, ArrowUpToLine, Loader2, PackageSearch } from 'lucide-vue-next'
import api from '../api/index'

const movements = ref([])
const loading = ref(true)

const fetchHistory = async () => {
  try {
    loading.value = true
    const response = await api.get('/stock-movement')
    movements.value = response.data || []
  } catch (error) {
    console.error('Erro ao carregar histórico:', error)
  } finally {
    loading.value = false
  }
}

onMounted(fetchHistory)

const formatDate = (dateString) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  }).format(date)
}

const formatCurrency = (value) => {
  if (value === null || value === undefined) return '-'
  return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}
</script>

<template>
  <div class="max-w-6xl">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-slate-900">Histórico</h1>
    </div>

    <div class="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
      <div v-if="loading" class="p-20 flex flex-col items-center justify-center text-slate-500">
        <Loader2 class="w-10 h-10 mb-4 animate-spin text-blue-500" />
        <p class="font-medium">Sincronizando registros...</p>
      </div>

      <div
        v-else-if="movements.length === 0"
        class="p-20 flex flex-col items-center justify-center text-slate-500"
      >
        <PackageSearch class="w-16 h-16 mb-4 text-slate-300" />
        <p class="text-lg font-medium text-slate-600">Nenhuma movimentação encontrada.</p>
        <p class="text-sm">As operações que você realizar aparecerão aqui.</p>
      </div>

      <table v-else class="w-full text-left text-sm text-slate-600">
        <thead class="bg-slate-50 border-b border-slate-200 text-slate-700">
          <tr>
            <th class="py-4 px-6 font-semibold uppercase text-xs tracking-wider">Data/Hora</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs tracking-wider">Produto</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs tracking-wider">Tipo</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs tracking-wider">Quantidade</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs tracking-wider">Preço de Venda</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="m in movements" :key="m.id" class="hover:bg-slate-50/50 transition-colors">
            <td class="py-4 px-6 whitespace-nowrap text-slate-500 font-mono">
              {{ formatDate(m.movementDate) }}
            </td>

            <td class="py-4 px-6">
              <div class="font-bold text-slate-900">{{ m.product.description }}</div>
              <div class="text-[10px] text-slate-400 uppercase tracking-tighter">
                {{ m.product.productType }}
              </div>
            </td>

            <td class="py-4 px-3">
              <span
                class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-[12px] font-black uppercase tracking-wide"
              >
                <ArrowDownToLine v-if="m.stockMovementStatus === 'ENTRADA'" class="w-3 h-3" />
                <ArrowUpToLine v-else class="w-3 h-3" />
                {{ m.stockMovementStatus }}
              </span>
            </td>

            <td class="py-4 px-6 font-bold text-slate-700">
              {{ m.movementQuantity }}
              <span class="text-[10px] font-normal text-slate-400 uppercase">un.</span>
            </td>

            <td class="py-4 px-6 font-medium">
              <span :class="m.salePrice ? 'text-slate-900' : 'text-slate-300 italic text-xs'">
                {{ formatCurrency(m.salePrice) }}
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
