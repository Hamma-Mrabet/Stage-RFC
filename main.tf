terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "3.112.0"
    }
  }
}
provider "azurerm" {
  features {}

  subscription_id = "6b266a6c-4027-4d13-b425-a99c964cebcf"
  tenant_id       = "604f1a96-cbe8-43f8-abbf-f8eaf5d85730"
  use_msi         = true
  
}

resource "azurerm_resource_group" "aks_rg" {
  name     = "Stage_Rfc1"
  location = "South Africa North"
}

resource "azurerm_kubernetes_cluster" "aks_cluster" {
  name                = "Stage_Rfc_Cluster1"
  location            = azurerm_resource_group.aks_rg.location
  resource_group_name = azurerm_resource_group.aks_rg.name

  dns_prefix          = "Stage-Rfc-Cluster11-dns"

  default_node_pool {
    name       = "agentpool"
    node_count = 1
    vm_size    = "Standard_DS2_v2"
  }

  network_profile {
    network_plugin    = "kubenet"
    load_balancer_sku = "standard"
  }

  identity {
    type = "SystemAssigned"
  }
}

output "kube_config" {
  value     = azurerm_kubernetes_cluster.aks_cluster.kube_config_raw
  sensitive = true
}
