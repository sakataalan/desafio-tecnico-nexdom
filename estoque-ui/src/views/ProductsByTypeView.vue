<script setup>
import { ref, onMounted, watch } from 'vue'
import { PackageSearch } from 'lucide-vue-next'
import api from '../api/index'

const results = ref([])
const loading = ref(false)
const selectedType = ref('')
const types = ref([])

const fetchTypes = async () => {
  try {
    const response = await api.get('/products/types')
    types.value = response.data || []
  } catch (error) {
    console.warn('Falha ao buscar tipos, usando lista padrão.', error)
  }
}

const fetchProductsByType = async () => {
  if (!selectedType.value) return

  try {
    loading.value = true
    const response = await api.get(`/products/by-type/${selectedType.value}`)
    results.value = response.data || []
  } catch (error) {
    console.error('Erro ao buscar produtos por tipo:', error)
    results.value = []
  } finally {
    loading.value = false
  }
}

onMounted(fetchTypes)

watch(selectedType, () => {
  fetchProductsByType()
})
</script>

<template>
  <div class="max-w-6xl">
    <div class="mb-8 flex flex-col md:flex-row md:items-center justify-between gap-4">
      <div>
        <h1 class="text-3xl font-bold text-slate-900">Consulta por Tipo</h1>
      </div>

      <div class="relative min-w-62.5">
        <div class="relative">
          <select
            v-model="selectedType"
            class="w-full px-4 py-3 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none text-sm bg-white"
          >
            <option value="" disabled selected>Escolha um tipo...</option>
            <option v-for="t in types" :key="t" :value="t">{{ t }}</option>
          </select>
        </div>
      </div>
    </div>

    <div class="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden min-h-100">
      <div
        v-if="!selectedType"
        class="p-20 flex flex-col items-center justify-center text-slate-400"
      ></div>

      <div
        v-else-if="results.length === 0"
        class="p-20 flex flex-col items-center justify-center text-slate-500"
      >
        <PackageSearch class="w-16 h-16 mb-4 text-slate-300" />
        <p class="text-lg font-medium text-slate-600">Nenhum produto deste tipo.</p>
      </div>

      <table v-else class="w-full text-left text-sm text-slate-600">
        <thead class="bg-slate-50 border-b border-slate-200 text-slate-700">
          <tr>
            <th class="py-4 px-6 font-semibold uppercase text-xs">Descrição</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs">Tipo</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs">Volume total de saída</th>
            <th class="py-4 px-6 font-semibold uppercase text-xs">Estoque Atual</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="p in results" :key="p.id" class="hover:bg-slate-50/50 transition-colors">
            <td class="py-4 px-6 font-bold text-slate-900">{{ p.description }}</td>
            <td class="py-4 px-6">
              <span class="text-slate-600">
                {{ p.productType }}
              </span>
            </td>
            <td class="py-4 px-6 font-mono">
              {{ p.totalMovementQuantity }}
            </td>
            <td class="py-4 px-6">
              <span class="px-3 py-1.5 text-slate-700"> {{ p.stockQuantity }} un. </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
