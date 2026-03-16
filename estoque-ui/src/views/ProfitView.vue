<script setup>
import { ref, onMounted } from 'vue'
import { Calendar, Search, XCircle, Loader2 } from 'lucide-vue-next'
import api from '../api/index'

const results = ref([])
const loading = ref(false)

const startDate = ref('')
const endDate = ref('')

const fetchProfit = async () => {
  try {
    loading.value = true

    const params = {}
    if (startDate.value) params.startDate = startDate.value
    if (endDate.value) params.endDate = endDate.value

    const response = await api.get('/products/profit', { params })
    results.value = response.data || []
  } catch (error) {
    console.error('Erro ao buscar lucro:', error)
    alert('Erro ao carregar dados financeiro.')
  } finally {
    loading.value = false
  }
}

const clearFilters = () => {
  startDate.value = ''
  endDate.value = ''
  fetchProfit()
}

onMounted(fetchProfit)

const formatCurrency = (value) => {
  return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value || 0)
}
</script>

<template>
  <div class="max-w-6xl">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-slate-900">Análise de Lucro</h1>
    </div>

    <div class="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 mb-8">
      <div class="flex flex-wrap items-end gap-6">
        <div class="flex-1 min-w-50">
          <label class="block text-xs font-bold text-slate-400 uppercase mb-2">Data Início</label>
          <div class="relative">
            <Calendar class="absolute left-3 top-3 w-4 h-4 text-slate-400" />
            <input
              v-model="startDate"
              type="date"
              class="w-full pl-10 pr-4 py-2.5 border border-slate-200 rounded-xl focus:ring-2 focus:ring-blue-500 outline-none text-sm"
            />
          </div>
        </div>

        <div class="flex-1 min-w-50">
          <label class="block text-xs font-bold text-slate-400 uppercase mb-2">Data Fim</label>
          <div class="relative">
            <Calendar class="absolute left-3 top-3 w-4 h-4 text-slate-400" />
            <input
              v-model="endDate"
              type="date"
              class="w-full pl-10 pr-4 py-2.5 border border-slate-200 rounded-xl focus:ring-2 focus:ring-blue-500 outline-none text-sm"
            />
          </div>
        </div>

        <div class="flex gap-2">
          <button
            @click="fetchProfit"
            class="bg-slate-900 text-white px-6 py-2.5 rounded-xl font-medium hover:bg-slate-800 transition-all flex items-center gap-2 text-sm"
          >
            <Search class="w-4 h-4" /> Filtrar
          </button>

          <button
            @click="clearFilters"
            class="bg-slate-100 text-slate-600 px-4 py-2.5 rounded-xl font-medium hover:bg-slate-200 transition-all text-sm"
            title="Limpar Filtros"
          >
            <XCircle class="w-4 h-4" />
          </button>
        </div>
      </div>
    </div>

    <div class="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden">
      <div v-if="loading" class="p-20 flex flex-col items-center justify-center text-slate-500">
        <Loader2 class="w-10 h-10 mb-4 animate-spin text-emerald-500" />
        <p>Calculando rentabilidade...</p>
      </div>

      <table v-else class="w-full text-left text-sm text-slate-600">
        <thead class="bg-slate-50 border-b border-slate-200 text-slate-700">
          <tr>
            <th class="py-4 px-6 font-semibold uppercase text-xs">Produto</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs text-center">Volume de Saída</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs text-right">Lucro Bruto</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="p in results" :key="p.id" class="hover:bg-slate-50/50 transition-colors">
            <td class="py-4 px-6 font-bold text-slate-900">
              {{ p.description }}
              <span class="block text-[10px] text-slate-400 font-normal mt-0.5"
                >ID: #{{ p.id }}</span
              >
            </td>
            <td class="py-4 px-6 text-center">
              <span> {{ p.totalOutputMovement }} vendas </span>
            </td>
            <td
              class="py-4 px-6 text-right font-mono font-bold text-base transition-colors"
              :class="p.profit < 0 ? 'text-rose-600' : 'text-emerald-600'"
            >
              {{ formatCurrency(p.profit) }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
